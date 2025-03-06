# Quarkus-Extension
Learning how to create Quarkus extension.

AES  and RSA Encryption/Decryption Service in Quarkus

Overview
This project implements an AES encryption and decryption service in Quarkus, using CDI (Context and Dependency Injection) to manage multiple AESUtil instances with different secret keys.

It ensures efficient secret key loading by initializing keys only once at application startup, preventing redundant property file reads on each method call.

Project Struture

src/main/java/com/util/crypto/extension/
│── deployment/
│   ├── CryptoUtilExtensionProcessor.java  # Registers AESUtil as a CDI bean
│── runtime/
│   ├── AESUtil.java                        # AES encryption/decryption logic
│   ├── RSAUtil.java                        # RSA encryption/decryption logic (if needed)
│   ├── AESUtilProducer.java                 # Produces multiple AESUtil instances
│   ├── AESConfigLoader.java                 # Loads secret keys only once at startup
│
└── com/siddhatech/person/
    ├── PersonResource.java                  # REST API for encryption & decryption


How It Works
1️⃣ AES Encryption Utility (AESUtil.java)
This class provides methods to encrypt and decrypt text using AES.

public class AESUtil {
    private String secretKey;

    public AESUtil(String secretKey) {
        this.secretKey = secretKey;
    }

    public String encrypt(String text) { ... }
    public String decrypt(String encryptedText) { ... }
}


Why Inject Multiple Instances?
We need three different instances of AESUtil, each initialized with a different secret key.

Dependency Injection (AESUtilProducer.java)
This class produces multiple named instances of AESUtil, each initialized with a different secret key.

@ApplicationScoped
public class AESUtilProducer {

    @Produces
    @ApplicationScoped
    @Named("aesUtil1")
    public AESUtil createAESUtil1(Config config) {
        String secretKey = config.getValue("security.aes.secretkey1", String.class);
        AESUtil aesUtil = new AESUtil();
        aesUtil.init(secretKey);
        return aesUtil;
    }

    @Produces
    @ApplicationScoped
    @Named("aesUtil2")
    public AESUtil createAESUtil2(Config config) {
        String secretKey = config.getValue("security.aes.secretkey2", String.class);
        AESUtil aesUtil = new AESUtil();
        aesUtil.init(secretKey);
        return aesUtil;
    }

    @Produces
    @ApplicationScoped
    @Named("aesUtil3")
    public AESUtil createAESUtil3(Config config) {
        String secretKey = config.getValue("security.aes.secretkey3", String.class);
        AESUtil aesUtil = new AESUtil();
        aesUtil.init(secretKey);
        return aesUtil;
    }
}

💡 Why Use a Producer?

CDI cannot inject different constructor parameters directly, so we manually produce AESUtil instances.
Uses @Named to differentiate between instances.

REST API (PersonResource.java)
This class exposes the encryption and decryption services as REST APIs.

Injecting Multiple AESUtil Instances

@Inject
@Named("aesUtil1")
AESUtil aesUtil1;

@Inject
@Named("aesUtil2")
AESUtil aesUtil2;

@Inject
@Named("aesUtil3")
AESUtil aesUtil3;


Encryption API

@GET
@Path("/encrypt/{text}")
public Response encrypt(@PathParam("text") String text) {
    try {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("encryptedText1", aesUtil1.encrypt(text));
        responseMap.put("encryptedText2", aesUtil2.encrypt(text));
        responseMap.put("encryptedText3", aesUtil3.encrypt(text));

        return Response.ok(responseMap).build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("error", "Encryption failed: " + e.getMessage()))
                .build();
    }
}


@POST
@Path("/decrypt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response decrypt(Map<String, String> encryptedTexts) {
    try {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("decryptedText1", aesUtil1.decrypt(encryptedTexts.get("encryptedText1")));
        responseMap.put("decryptedText2", aesUtil2.decrypt(encryptedTexts.get("encryptedText2")));
        responseMap.put("decryptedText3", aesUtil3.decrypt(encryptedTexts.get("encryptedText3")));

        return Response.ok(responseMap).build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("error", "Decryption failed: " + e.getMessage()))
                .build();
    }
}


Example Input (JSON) for /decrypt API:
{
    "encryptedText1": "dUNa/8j2APH2rNRZ1E853q7xvvykFFw04mMo01hqIPo=",
    "encryptedText2": "b825hcdSkn3VB2uYBe3Vsxc96Vxa1oL1KTMsMNEFFjs=",
    "encryptedText3": "/9ixErJsFGePRM/Ik2kEywY7dQG3rDJSoj4TIQfzJgg="
}

Configuration (application.properties)
Ensure that the secret keys are present in the main application's configuration file:

security.aes.secretkey1=your-secret-key-1
security.aes.secretkey2=your-secret-key-2
security.aes.secretkey3=your-secret-key-3


Key Features
✅ Multiple AES Instances → Each AESUtil instance is configured with a different secret key.
✅ Efficient Config Management → Secret keys are loaded only once at startup.
✅ Quarkus CDI Injection → Uses @Named qualifiers to inject different beans.
✅ REST API → Supports encryption & decryption via JSON requests.
✅ Performance Optimized → No redundant config reads, uses in-memory keys.



Conclusion
This project efficiently manages multiple AES encryption utilities using Quarkus CDI, ensuring optimal performance by loading secret keys only once. Using producer beans allows seamless dependency injection of different AES instances.

🚀 Now, your encryption & decryption service is scalable and optimized!

