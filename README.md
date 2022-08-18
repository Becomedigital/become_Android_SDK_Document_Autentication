# Documentación de Become Android SDK

### Implementación de módulos requeridos
Cómo primera medida es necesaria la implementacion de los siguientes módulos y configuraciones:

    defaultConfig {
     minSdkVersion 21
    }
		 
    android {
      compileOptions {
		sourceCompatibility = 1.8
		targetCompatibility = 1.8
      }
    }

    implementation fileTree(dir: 'libs', include: ['*.aar'])
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.navigation:navigation-fragment:2.3.0'
    implementation 'androidx.navigation:navigation-ui:2.3.0'
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    implementation 'com.squareup.okhttp3:okhttp:4.2.2'
  
### Agregar licencia requerida

1. Agregar licencia en los assets del proyecto:

<p align="center">
  <img src="https://github.com/Becomedigital/become_ANDROID_SDK_ADC/blob/main/assets_key.png">
</p>

 2. El [`applicationId`](https://developer.android.com/studio/build/application-id?hl=es-419) del proyecto debe coincidir con la licencia asignada al cliente:

### Implementación de la SDK Become
       
 1. Descargue las librerías `becomedigitalsdk.aar` y `BlinkID.aar`, a continuación agréguelas en las dependencias binarias locales de su proyecto.

<p align="center">
  <img src="https://github.com/Becomedigital/become_Android_SDK_Document_Autentication/blob/main/libs.png">
</p>
		 
 2. Al realizar los pasos anteriores, debe sincronizar su proyecto con gradle.
 
 ## Inicialización de la SDK
 
**En el método `onCreate ()` de su `Activity` en su aplicación, inicialice Become para la captura de imágenes, se debe asignar el `ItFirstTransaction` como True, puedes utilizar el siguiente fragmento de código:**

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
	
En el método `secondAction ()` de su `Activity` de aplicación, inicialice Become y proceda al el envío de la imagen del documento para su posterior validación, se debe asignar el `ItFirstTransaction` como False, Y el parámetro imagen debe estar cargado con la información de la imagen completa por el anverso del documento, puedes utilizar el siguiente fragmento de código:**

Nota: para obtener información de la registraduría nacional de Colombia, se requiere se adicione el parámetro `documentNumber`, el cual es retornado por el primer llamado a la SDK. 
 
        //Parámetros de configuración: El valor de los parámetros debe ser solicitado al contratar el servicio
	
	String token =  "your bearer token here" ;  
	String contractId =  "your contract ID here";
	String userId = "your user ID here"
	String frontImagePath = "Image path, returned by the first event"
  
	BecomeResponseManager.getInstance().startAutentication(MainActivity.this,
	 new BDIVConfig(false,
                        token,
                        contractId,
                        userId,
                        glovalresponseIV.getDocumentNumber(),
                        glovalresponseIV.getIsoAlpha2CountryCode(),
                        glovalresponseIV.getTypeOrdinal(),
                        glovalresponseIV.getFullFronImagePath(),
                        glovalresponseIV.getBackImagePath(),
                        glovalresponseIV.getBarcodeResult()
        ));
		
## Cambiar textos predeterminados en la SDK     
	
Para cambiar algún texto predeterminado y asignar uno en reemplazo de este, cree una carpeta ejemplo: `values-es` en su proyecto y coloque la copia de `strings.xml` dentro. agrege la eqtiqueta del texto a remplazar ejemplo: `<string name="mb_blinkid_front_instructions">Escanea la parte frontal\ndel documento</string>`.

Documentacion oficial android [Cómo localizar tu app](https://developer.android.com/guide/topics/resources/localization)

**Listado de llaves**

	<resources>
	    <string name="mb_activity_title_step_back_side">Lado posterior</string>
	    <string name="mb_activity_title_step_front_side">Lado frontal</string>
	    <string name="mb_autofocus_fail">La cámara no puede enfocar. Intente escanear con luz diferente.</string>
	    <string name="mb_blinkid_back_instructions">Escanea la parte de atrás del documento</string>
	    <string name="mb_blinkid_back_instructions_barcode">Escanea el código de barras</string>
	    <string name="mb_blinkid_camera_flip_document">Dele la vuelta al documento</string>
	    <string name="mb_blinkid_document_not_fully_visible">Mantenga el documento visible en su totalidad</string>
	    <string name="mb_blinkid_document_too_close_to_edge">Mueva el documento desde el borde</string>
	    <string name="mb_blinkid_front_instructions">Escaneo de la parte frontal\nde un documento</string>
	    <string name="mb_camera_not_allowed">La cámara no está permitida por la configuración de permisos.</string>
	    <string name="mb_camera_not_ready">La cámara no está lista.</string>
	    <string name="mb_camera_permission_required">Se requiere permiso de cámara.</string>
	    <string name="mb_close">Cerrar</string>
	    <string name="mb_custom_ui_forbidden">¡La licencia no permite la actividad de la cámara personalizada!</string>
	    <string name="mb_data_not_match_msg">Por favor, inicie el proceso de escaneo de nuevo.</string>
	    <string name="mb_data_not_match_retry_button">Reintentar</string>
	    <string name="mb_data_not_match_title">Los lados no coinciden</string>
	    <string name="mb_dismiss_error_dialog">Cerrar</string>
	    <string name="mb_enable_camera">Activar cámara</string>
	    <string name="mb_enable_permission_help">Ahora se le redirigirá a la configuración de permisos, donde tendrá que otorgar a la aplicación permiso de cámara.</string>
	    <string name="mb_enable_permission_help_instant_app">Dele permiso a la cámara en la configuración de permisos de la aplicación.</string>
	    <string name="mb_error_camera_high">Acercarse</string>
	    <string name="mb_error_camera_near">Alejarse</string>
	    <string name="mb_error_initializing">No se pudo inicializar la biblioteca nativa. Compruebe el registro de errores de ADB para obtener más detalles.</string>
	    <string name="mb_feature_unsupported_android_version">Esta función es compatible con Android 2.1 y versiones posteriores</string>
	    <string name="mb_feature_unsupported_autofocus">Esta función no es compatible con la cámara sin enfoque automático</string>
	    <string name="mb_feature_unsupported_device">Esta función no es compatible debido a las propiedades técnicas de su dispositivo.</string>
	    <string name="mb_flashlight">Linterna</string>
	    <string name="mb_flashlight_warning_message">Tenga cuidado con el resplandor de la linterna.\nMueva suavemente su ID a su alrededor para evitarlo.</string>
	    <string name="mb_help">Ayuda</string>
	    <string name="mb_invalid_license">¡Clave de licencia no válida! Compruebe el registro de errores de ADB para obtener más detalles.</string>
	    <string name="mb_licence_check_device_offline">Verifique su conexión a Internet</string>
	    <string name="mb_licence_check_failed">Error de red</string>
	    <string name="mb_licence_locked">Escaneo no disponible</string>
	    <string name="mb_recognition_timeout_dialog_message">No se puede leer el documento. Por favor, inténtelo de nuevo.</string>
	    <string name="mb_recognition_timeout_dialog_retry_button">Reintentar</string>
	    <string name="mb_recognition_timeout_dialog_title">Escaneo fallido</string>
	    <string name="mb_something_went_wrong">Algo ha ido mal</string>
	    <string name="mb_splash_msg_id_back">Parte posterior de la tarjeta de identificación</string>
	    <string name="mb_splash_msg_id_front">Parte frontal de la tarjeta de identificación</string>
	    <string name="mb_tooltip_back_id">Coloque el <b>LADO POSTERIOR</b> de la tarjeta de identificación en el marco y espere a que se escanee automáticamente</string>
	    <string name="mb_tooltip_front_id">Coloque el <b>LADO FRONTAL</b> de la tarjeta de identificación en el marco y espere a que se escanee automáticamente</string>
	    <string name="mb_tooltip_glare">Mueva ligeramente la identificación para eliminar el deslumbramiento.</string>
	    <string name="mb_try_scanning_again">Parece que la información del documento no es consistente. Intente escanear de nuevo.</string>
	    <string name="mb_unsupported_document_message">Escanee la parte frontal de un documento compatible.</string>
	    <string name="mb_unsupported_document_title">Documento no reconocido</string>
	    <string name="mb_warning_title">Advertencia</string>
	</resources>


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
    private String documentNumber;
    private String dateOfExpiry;
    private Integer age;
    private String dateOfBirth;
    private String placeOfBirth;
    private String dateOfIssue;
    private String mrzText;
    private String sex;
    private String barcodeResult;
    private byte[] barcodeResultData;
    private String isoAlpha2CountryCode;
    private String isoAlpha3CountryCode;
    private String isoNumericCountryCode;
    private String countryName;
    private String type;
    private Integer typeOrdinal;
    private String frontImagePath;
    private String backImagePath;
    private String fullFronImagePath;
    private String fullBackImagePath;
    private String faceImagePath;
    private String documentValidation;
    private String registryInformation;
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

