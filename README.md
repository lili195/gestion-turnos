# ⏭️ Turns Management App 👩‍💻

The purpose of this project is to develop an information system to improve the process of managing turns in a facility and to be able 
to follow up on them. On countless occasions in daily life we find that we have to wait in person to be attended, either in public or 
private entities. An example of this is the queue to wait for a table in any busy restaurant. This implies being physically present in
the place during the whole waiting time before our turn, forcing us to be unable to perform any other task.

This application aims to solve this issue by allowing users to reserve their place in line remotely and receive real-time
updates on their status, freeing them from the need to wait in person. The app offers flexibility and convenience,
significantly improving the overall waiting experience and reducing inefficiencies in service management.

<h2>Functionalities</h2>

- Create, log in and manage their acount.
- Create, view and cancel a turn.
- Administrators can create new turns for registered users.
- Administrators can cancel turns created by registered users.

<h2>Technologies</h2>

- <b>Back:</b> Springboot 
- <b>Front:</b> React 
- <b>Databases:</b> Neo4J, MongoDB, MySQL</b>

<h2>Other Relevant Information</h2>

- The system architecture is microservices.
- Authentication method based on the KeyCloak architecture.
- Project persistence managed with JPA.
- Event bus to expose microservices to both other microservices and users.
