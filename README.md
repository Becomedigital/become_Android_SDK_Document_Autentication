# Documentación de Become Android SDK
Este es un espacio para conocer a cerca del SDK Android de Become para la validación de identidad.
<p align="center">
  <img src="https://github.com/Becomedigital/become_ANDROID_SDK/blob/master/Pantalla_Android.png" width="284" height="572">
</p>

## Configuraciones de Gradle

 1. Dentro del archivo build.gradle debe agregar las siguientes modificaciones:

	         defaultConfig {
	           minSdkVersion 21
	         }
		 
		 android {
		    compileOptions {
		        sourceCompatibility = 1.8
			targetCompatibility = 1.8
		     }
		}
				
2. El archivo build.grade debe contar con una referencia al repositorio:
	
		maven { url 'https://jitpack.io' }

<p align="center">
  <img src="https://github.com/Becomedigital/become_ANDROID_SDK/blob/master/build_gradle.png">
</p>

### Implementación de módulos requeridos
Cómo primera medida es necesaria la implementacion de los siguientes módulos:

    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.navigation:navigation-fragment:2.3.0'
    implementation 'androidx.navigation:navigation-ui:2.3.0'
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    implementation 'com.squareup.okhttp3:okhttp:4.2.2'
    implementation('com.microblink:blinkid:5.16.0@aar') {
        transitive = true
    }
  
### Agregar licencia requerida

1. Agregar licencia en los assets del proyecto:

<p align="center">
  <img src="https://github.com/Becomedigital/become_ANDROID_SDK_ADC/blob/main/assets_key.png">
</p>

 2. El [`applicationId`](https://developer.android.com/studio/build/application-id?hl=es-419) del proyecto debe coincidir con la licencia asignada al cliente:

### Implementación de la SDK Become
       
 1. Abra el archivo build.gradle dentro del directorio del módulo de su aplicación e incluya las siguientes dependencias en el archivo build.gradle de su aplicación:
 
		 implementation 'com.github.Becomedigital:become_Android_SDK_Document_Autentication:LATEST_VERSION'
		 
	Ejemplo:
		 
		 implementation 'com.github.becomedigital:become_Android_SDK_Document_Autentication:1.0'
		 
 2. Al realizar los pasos anteriores, debe sincronizar su proyecto con gradle.
 
 ## Inicialización de la SDK
 
**En el método **onCreate ()** de su **Activity** en su aplicación, inicialice Become para la captura de imágenes, se debe asignar el **ItFirstTransaction** como True, puedes utilizar el siguiente fragmento de código:**

	public class MainActivity extends AppCompatActivity {
    
    //Con el fin de manejar las respuestas de inicio de sesión, debe crear un callback utilizando el siguiente fragmento de código
    private BecomeCallBackManager mCallbackManager = BecomeCallBackManager.createNew ( );

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate (savedInstanceState);
	    setContentView (R.layout.activity_main);

        //Parámetros de configuración: El valor de los parámetros debe ser solicitado al contratar el servicio
        String token =  "your bearer token here" ;  
        String contractId =  "your contract ID here";
        String userId = "your user ID here"
	
        //Instancia para iniciar la interfaz
      BecomeResponseManager.getInstance().startAutentication(MainActivity.this,
                    new BDIVConfig(true,
                            token,
                            contractId,
                            userId));
	  }
	}
	
**3. En el método **secondAction ()** de su **Activity** de aplicación, inicialice Become y proceda al el envío de la imagen del documento para su posterior validación, se debe asignar el **ItFirstTransaction** como False, Y el parámetro imagen debe estar cargado con la información de la imagen completa por el anverso del documento, puedes utilizar el siguiente fragmento de código:**
 
        //Parámetros de configuración: El valor de los parámetros debe ser solicitado al contratar el servicio
	
        String token =  "your bearer token here" ;  
        String contractId =  "your contract ID here";
        String userId = "your user ID here"
        BecomeResponseManager.getInstance().startAutentication(MainActivity.this,
                new BDIVConfig(false,
                        token,
                        contractId,
                        userId,
                        frontImagePath
                ));
	

## Posibles Errores

**1. Error con parámetros vacíos** 
Los siguientes parámetros son necesarios para la activación de la SDK por lo tanto su valor no debe ser vacío.
 
Parámetro | Valor
------------ | -------------
contractId | String
userId  | String
ItFirstTransaction  | Bool


Mostrará el siguiente error por consola:

    parameters cannot be empty

## Validación del proceso
En este apartado encontrará la respuesta a partir de la validación del proceso realizado por la SDK y las estructuras internas que contienen los atributos encargados de capturar la información dada por el usuario:

**1. Estructura encargada de la definición del estado  de validación ***exitoso***:**

	    @Override
        public void onSuccess(final ResponseIV responseIV) {  
        }
	
**2. Estructura encargada de la definición del estado  de validación ***cancelado*** por el usuario:**

	@Override  
	public void onCancel() { 
	}
   
**3. Estructura encargada de la definición del estado  de validación ***error*** , este estado se presenta cuándo ocurren errores generales o de inicialización de parámetros :**

	 @Override  
	 public void onError(LoginError pLoginError) {
	 }

## Estructura para el retorno de la información
Los siguientes son los parámetros que permiten el retorno de la información capturada por el sistema.

    private String firstName;
    private String lastName;
    private String dateOfExpiry;
    private Integer age;
    private String dateOfBirth;
    private String mrzText;
    private String sex;
    private String barcodeResult;
    private byte[] barcodeResultData;
    private String frontImagePath;
    private String backImagePath;
    private String fullFronImagePath;
    private String fullBackImagePath;
    private String documentValidation;
    private Integer responseStatus;
    private String message;
    private Boolean IsFirstTransaction;

Ejemplo de la respuesta:

	        @Override
                public void onSuccess(final ResponseIV responseIV) {
                    runOnUiThread(() -> {
                        if (responseIV.getFullFronImagePath() != null) {
                            File imgFileFullFront = new File(responseIV.getFullFronImagePath());
                            if (imgFileFullFront.exists()) {
                                fullFronImagePath = responseIV.getFullFronImagePath();
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFileFullFront.getAbsolutePath());
                                imgFrontFull.setImageBitmap(myBitmap);
                            }
                        }
                        if (responseIV.getFrontImagePath() != null) {
                            File imgFile = new File(responseIV.getFrontImagePath());
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imgFront.setImageBitmap(myBitmap);
                            }
                        }
                        if (responseIV.getBackImagePath() != null) {
                            File imgFile = new File(responseIV.getBackImagePath());
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imgBack.setImageBitmap(myBitmap);
                            }
                        }
                        if (responseIV.getFullBackImagePath() != null) {
                            File imgFile = new File(responseIV.getFullBackImagePath());
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imgBackFull.setImageBitmap(myBitmap);
                            }
                        }
                        textResponse.setText(responseIV.toString());
                    });
                }
		
## Implementación del proceso
Esta sección se encarga de proporcionar el fragmento de código para la implementación final del proceso.

	
        public class MainActivity extends AppCompatActivity {
	    private final BecomeCallBackManager mCallbackManager = BecomeCallBackManager.createNew();
	    private String contractId = "";
	    private String token = "";
	    private String userId = "";
	    private TextView textResponse;
	    private String fullFronImagePath = "";
	    private TextView textValidation;

	    @SuppressLint("WrongThread")
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textResponse = findViewById(R.id.textReponse);
		textValidation = findViewById(R.id.textDocumentValidation);
		EditText textContractId = findViewById(R.id.ContractIdText);
		EditText textToken = findViewById(R.id.textToken);
		ImageView imgFront = findViewById(R.id.imgFront);
		ImageView imgFrontFull = findViewById(R.id.imgFrontFull);
		ImageView imgBack = findViewById(R.id.imgBack);
		ImageView imgBackFull = findViewById(R.id.imgBackFull);
		Button btnAut = findViewById(R.id.btnAuth);
		Button btnSecond = findViewById(R.id.btnSecond);

		btnSecond.setOnClickListener(view -> {
		    textValidation.setText("Enviando segunda petición...");
		    secondTransacion(fullFronImagePath);
		});
		btnAut.setOnClickListener(view -> {
		    textValidation.setText("");
		    token = textToken.getText().toString();
		    contractId = textContractId.getText().toString().isEmpty() ? "2" : textContractId.getText().toString();
		    Date currentTime = Calendar.getInstance().getTime();
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
		    userId = format1.format(currentTime);

		    BecomeResponseManager.getInstance().startAutentication(MainActivity.this,
			    new BDIVConfig(true,
				    token,
				    contractId,
				    userId));

		    BecomeResponseManager.getInstance().registerCallback(mCallbackManager, new BecomeInterfaseCallback() {
			@Override
			public void onSuccess(final ResponseIV responseIV) {
			    runOnUiThread(() -> {
				if (responseIV.getFullFronImagePath() != null) {
				    File imgFileFullFront = new File(responseIV.getFullFronImagePath());
				    if (imgFileFullFront.exists()) {
					fullFronImagePath = responseIV.getFullFronImagePath();
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFileFullFront.getAbsolutePath());
					imgFrontFull.setImageBitmap(myBitmap);
				    }
				}
				if (responseIV.getFrontImagePath() != null) {
				    File imgFile = new File(responseIV.getFrontImagePath());
				    if (imgFile.exists()) {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
					imgFront.setImageBitmap(myBitmap);
				    }
				}
				if (responseIV.getBackImagePath() != null) {
				    File imgFile = new File(responseIV.getBackImagePath());
				    if (imgFile.exists()) {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
					imgBack.setImageBitmap(myBitmap);
				    }
				}
				if (responseIV.getFullBackImagePath() != null) {
				    File imgFile = new File(responseIV.getFullBackImagePath());
				    if (imgFile.exists()) {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
					imgBackFull.setImageBitmap(myBitmap);
				    }
				}
				textResponse.setText(responseIV.toString());
			    });
			}

			@Override
			public void onCancel() {
			    textResponse.setText("Cancelado por el usuario ");

			}

			@Override
			public void onError(LoginError pLoginError) {
			    textResponse.setText(pLoginError.getMessage());
			}

		    });
		});

	    }

	    private void secondTransacion(String frontImagePath) {

		BecomeResponseManager.getInstance().startAutentication(MainActivity.this,
			new BDIVConfig(false,
				token,
				contractId,
				userId,
				frontImagePath
			));

		BecomeResponseManager.getInstance().registerCallback(mCallbackManager, new BecomeInterfaseCallback() {
		    @Override
		    public void onSuccess(final ResponseIV responseIV) {
			runOnUiThread(() -> {
			    textValidation.setText(responseIV.getDocumentValidation());
			});
		    }

		    @Override
		    public void onCancel() {
			runOnUiThread(() -> {
			    textValidation.setText("Cancelado por el usuario ");
			});
		    }

		    @Override
		    public void onError(LoginError pLoginError) {
			runOnUiThread(() -> {
			    textValidation.setText(pLoginError.getMessage());
			});
		    }

		});
	    }
	}


## Requerimientos

* **Tecnologias**
	
	Android 5 en adelante
	gradle-7.0.2
	
## Vídeo de integración del SDK Become para Android
[![Become](https://github.com/Becomedigital/become_ANDROID_SDK/blob/master/Video.png)](https://www.youtube.com/watch?v=ggpLGHeaFSg&ab_channel=BecomeDigital)

