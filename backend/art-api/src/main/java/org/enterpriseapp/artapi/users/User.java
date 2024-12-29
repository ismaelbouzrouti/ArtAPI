package org.enterpriseapp.artapi.users;
import jakarta.persistence.*;


    // User Entity
    @Entity
    @Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String userName;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        //constructors

        public User(String userName, Long id, String password, String email, String firstName, String lastName) {
            this.userName = userName;
            this.id = id;
            this.password = password;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(){}


        // Getters and Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String username) {
            this.userName = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public String toString(){

            return "User entity: " + "username: " + userName + "\n email: " + email + "\n password: " + password + "\n firstname: " + firstName +
                    "\n lastname: " + lastName;
        }
    }


