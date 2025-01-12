package org.enterpriseapp.artapi.users;
import jakarta.persistence.*;
import org.enterpriseapp.artapi.reservation.Reservation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


// User Entity
    @Entity
    @Table(name = "users")
    public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment id
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

        @Column(nullable = false)
        private boolean isAdmin;

        //a user can have many reservations
        // the reservation holds the foreign key = user_id
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) //if a user is deleted his reservations are also deleted
        private List<Reservation> reservations;


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

        @Override
        public String getUsername() {
            return userName;
        }

        public void setUserName(String username) {
            this.userName = username;
        }

//    assigns roles to the user based on their admin status
//    returns "ROLE_ADMIN" if the user is an admin, else "ROLE_USER"
//    this is used by Spring Security for role-based access control
//    it returns the roles and authorities of the user based on the field isAdmin
//    when user logs in its details are fetched from the db and put into a authentication context
//    this way spring can check if user is admin or not
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin) {
            return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    public String getPassword() {
            return password;
        }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
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

        public boolean getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean admin) {
            isAdmin = admin;
        }

        @Override
        public String toString(){

            return "User entity: " + "username: " + userName + "\n email: " + email + "\n password: " + password + "\n firstname: " + firstName +
                    "\n lastname: " + lastName;
        }


    }


