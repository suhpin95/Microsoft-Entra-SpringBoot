# Getting Started

### Microsoft Entra
Entra is one of the managed service from Microsoft that can be used for Authorization and Authentication.
This is an attempt to learn about authorization on a protected route using scopes. Though the code is borrowed
from the documentation of Entra which will be shared below, this is an earnest attempt to learn about Entra.[Also I didn't want to waste efforts and time on building a new Endpoints 😎]

### Requirements
1. Java 8+
2. Spring-Boot 2.5 +

### Reference Documentation

For further reference, please consider the following sections:
1. https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/secure-your-restful-api-using-spring-cloud-azure
2. https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/spring-boot-starter-for-entra-developer-guide?tabs=SpringCloudAzure5x

### OAuth2 Token Generation Using Postman

#### Overview
This guide provides a step-by-step approach to obtaining an OAuth2 token using **Postman**. It is intended for engineers working with authentication in distributed systems, specifically leveraging **Microsoft Entra ID** for secure API access.

#### Prerequisites
Before proceeding, ensure you have the following:
- **Registered Application in Microsoft Entra ID** (formerly Azure AD)
- **Client ID** (from Azure portal)
- **Client Secret** (if using client credentials flow)
- **Tenant ID**
- **OAuth2 Token Endpoint** (available in the Azure portal under the app's **Endpoints** section)
- **Postman** installed

#### Steps to Obtain an OAuth2 Token

#### 1️⃣ Open Postman
Launch **Postman** and go to the **Authorization** tab.

#### 2️⃣ Select OAuth 2.0
```plaintext
- Under "Type", choose "OAuth 2.0".
- Click on "Get New Access Token".
```

#### 3️⃣ Configure OAuth 2.0 Settings
Fill in the following details:

```plaintext
Grant Type: Authorization Code (or Client Credentials for machine-to-machine auth)
Auth URL: https://login.microsoftonline.com/{tenant-id}/oauth2/v2.0/authorize
Access Token URL: https://login.microsoftonline.com/{tenant-id}/oauth2/v2.0/token
Client ID: Your registered application’s Client ID
Client Secret: Your application's secret (if applicable)
Scope: api://{your-api-client-id}/Survey.User
Redirect URL: https://oauth.pstmn.io/v1/callback (for Postman authentication flows)
```

🔹 **Note**: Replace `{tenant-id}` with your Azure **Directory (tenant) ID** and `{your-api-client-id}` with your API's **Application ID**.

#### 4️⃣ Get the Token
```plaintext
- Click "Request Token".
- Authenticate using Microsoft login if prompted.
- Once authenticated, Postman will fetch and display the Access Token.
```

#### 5️⃣ Use the Token in API Requests
```plaintext
- Under the "Authorization" tab, select "Bearer Token".
- Paste the retrieved Access Token.
- Execute API requests with the token attached.
```

### API Endpoints

#### `api/survey`
This endpoint supports both **POST** and **GET** requests and requires a **Bearer Token**.

- **POST `/api/survey`**: Can be accessed by any user with the **Survey.User** scope.
  ```plaintext
  - Method: POST
  - Headers: Authorization: Bearer {access_token}
  - Body: { "answer": "Your response" }
  ```
    - Response: "The response has been noted"

- **GET `/api/survey`**: Restricted to **Admin users** only.
  ```plaintext
  - Method: GET
  - Headers: Authorization: Bearer {access_token}
  ```
    - Response: List of survey responses

### Troubleshooting
#### ❌ `invalid_request` Error
```plaintext
- Ensure Tenant ID, Client ID, and Scope are correct.
- Verify that the redirect URL is registered in the Azure portal.
```

#### ❌ `AADSTS700016: Application with identifier not found`
```plaintext
- Confirm that the Client ID matches the one registered in Entra ID.
- Ensure that your app is added under API permissions in Azure.
```

#### ❌ `AADSTS900023: Specified tenant identifier is neither a valid DNS name nor an external domain`
```plaintext
- Double-check that you are using the correct Tenant ID, not the Application ID.
```

#### Conclusion
By following this guide, you can successfully generate and use an **OAuth2 Access Token** using Postman for secure API authentication. If you are working in a **distributed microservices architecture**, ensure token validation is implemented at the **gateway layer** for enhanced security.

For further troubleshooting, consult [Microsoft's OAuth2 Documentation](https://learn.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow).


