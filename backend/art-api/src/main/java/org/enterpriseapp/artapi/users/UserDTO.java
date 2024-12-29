package org.enterpriseapp.artapi.users;

// User DTO
public class UserDTO {


        private Long id;
        private String userName;
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        // Constructors

        public UserDTO() {}

    public UserDTO(Long id, String userName, String email, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
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

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getPassword(){
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
    }

    @Override
    public String toString(){

            return "userDTO: " + "username: " + userName + "\n email: " + email + "\n password: " + password + "\n firstname: " + firstName +
                    "\n lastname: " + lastName;
    }
}


