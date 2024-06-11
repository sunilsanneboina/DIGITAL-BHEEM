import java.util.*;
// Enum for user types
enum UserType {
    CUSTOMER,
    ADMIN
}
// to represent a User
class User {
    private String username;
    private String password;
    private UserType userType;
    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public UserType getUserType() {
        return userType;
    }
}
//  to manage user accounts
class UserManager {
    private List<User> users;
    public UserManager() {
        this.users = new ArrayList<>();
    }
    public void registerUser(String username, String password, UserType userType) {
        users.add(new User(username, password, userType));
    }
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; //if  User not found or incorrect credentials
    }
}
//to represent a Movie
class Movie {
    private String title;
    private String description;
    private List<String> showtimes;
    public Movie(String title, String description, List<String> showtimes) {
        this.title = title;
        this.description = description;
        this.showtimes = showtimes;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public List<String> getShowtimes() {
        return showtimes;
    }
}
//to manage movies
class MovieManager {
    private List<Movie> movies;
    public MovieManager() {
        this.movies = new ArrayList<>();
    }
    public void addMovie(String title, String description, List<String> showtimes) {
        movies.add(new Movie(title, description, showtimes));
    }
    public List<Movie> getMovies() {
        return movies;
    }
}
//to represent a Booking
class Booking {
    private User user;
    private Movie movie;
    private String showtime;
    private List<String> seats;
    public Booking(User user, Movie movie, String showtime, List<String> seats) {
        this.user = user;
        this.movie = movie;
        this.showtime = showtime;
        this.seats = seats;
    }
    public User getUser() {
        return user;
    }
    public Movie getMovie() {
        return movie;
    }
    public String getShowtime() {
        return showtime;
    }
    public List<String> getSeats() {
        return seats;
    }
}
//to manage bookings
class BookingManager {
    private List<Booking> bookings;
    public BookingManager() {
        this.bookings = new ArrayList<>();
    }
    public void bookTicket(User user, Movie movie, String showtime, List<String> seats) {
        bookings.add(new Booking(user, movie, showtime, seats));
    }
    public List<Booking> getUserBookings(User user) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUser().equals(user)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }
}
//to handle payment processing
class PaymentGateway {
    public boolean processPayment(double amount, String cardNumber, String expiryDate, String cvv) {
        return true; // Dummy implementation
    }
}
//for the Movie Ticket Booking System
public class MovieTicketBookingSystem {
    public static void main(String[] args) {
        // Initialize managers and other necessary objects
        UserManager userManager = new UserManager();
        MovieManager movieManager = new MovieManager();
        BookingManager bookingManager = new BookingManager();
        PaymentGateway paymentGateway = new PaymentGateway();
        // Registering users (Admin and Customer)
        userManager.registerUser("admin", "admin123", UserType.ADMIN);
        userManager.registerUser("user", "user123", UserType.CUSTOMER);
        // Admin login
        User admin = userManager.login("admin", "admin123");
        if (admin != null && admin.getUserType() == UserType.ADMIN) {
            // Add movies (Admin functionality)
            List<String> showtimes = Arrays.asList("10:00 AM", "2:00 PM", "6:00 PM");
            movieManager.addMovie("Movie 1", "Description 1", showtimes);
            movieManager.addMovie("Movie 2", "Description 2", showtimes);
        } else {
            System.out.println("Admin login failed");
            return;
        }
        // Customer login
        User customer = userManager.login("user", "user123");
        if (customer != null && customer.getUserType() == UserType.CUSTOMER) {
            // Get movie listings
            List<Movie> movies = movieManager.getMovies();
            for (Movie movie : movies) {
                System.out.println("Movie: " + movie.getTitle() + ", Description: " + movie.getDescription());
                System.out.println("Showtimes: " + movie.getShowtimes());
            }
            // Book a ticket
            List<String> selectedSeats = Arrays.asList("A1", "A2");
            bookingManager.bookTicket(customer, movies.get(0), "10:00 AM", selectedSeats);
            // View booking history
            List<Booking> userBookings = bookingManager.getUserBookings(customer);
            System.out.println("Booking History:");
            for (Booking booking : userBookings) {
                System.out.println("Movie: " + booking.getMovie().getTitle() + ", Showtime: " + booking.getShowtime());
                System.out.println("Seats: " + booking.getSeats());
            }
            // Payment processing
            boolean paymentStatus = paymentGateway.processPayment(50.00, "1234 5678 9012 3456", "12/25", "123");
            if (paymentStatus) {
                System.out.println("Payment successful");
            } else {
                System.out.println("Payment failed");
            }
        } else {
            System.out.println("Customer login failed");
        }
    }
}