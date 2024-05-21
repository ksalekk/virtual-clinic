## Virtual Clinic

# Features
Simple web app developed for Security of Medical IT Systems course at the WUT. The app offers services for multiple roles - Patient, Doctor and Staff/Receptionist. User with any role can log in and sign up; other functionalities are designed for specified role. Paient can edit own personal data and see own appointemnts, doctor can see own appointments and edit some of things for specified appointment (e.g. summary description). Staff/Receptionist can see all doctors and patients in system and add new appointment for specified patient and doctor.


# Architecture and Security
The backend is built on Spring Boot, while the frontend is developed using React JS (communication based on REST API). MySQL serves as the database for storing application data. Security things are implemented "from stratch" (i.e. no security frameworks).

Authentication is based on basic HTTP authentication - credentials are encoded in base64 and transferred in HTTP header from client to server (encode64(<username>:<password>)). Backend decode credentials, compute hash (SHA-256) for decoded password and compare with password hash from the database. If authentication is successful, backend return HTTP response with specified Set-Cookie header and principal body (user id and role). Session is stored with HttpOnly cookie for client side and in HttpSession for backend side (in Spring context).

Authorization is implemented with use Spring filters that processing every http request before reaching DispatcherServlet. Decision about accept/reject request is taken based on user role and target endpoint. Authorization filter is whitelisting oriented and use AuthGuard object to check if specified request can access the resource. Moreover, REST API must also prevent from getting access to user B resource by user A with the same role (Insecure Direct Object Reference), so additional layer was implemented at the lowest cost, i.e. in controllers (in the real system it would be better/cleaner to implement it with Spring aspects).

# Run
The app is contenerized with docker so you can deploy the application on your own computer with the following steps:
```
git clone 
docker-compose -f docker-compose.yaml up
```
