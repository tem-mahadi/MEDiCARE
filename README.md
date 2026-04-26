# 🏥 MEDiCARE: Your Premium Personal Health Assistant

MEDiCARE is a robust, offline-capable healthcare application designed to simplify medical activities. It enables users to securely subscribe via an OTP flow, book doctor appointments, arrange lab tests, calculate their BMI, and access emergency services. The app features a powerful local database that allows users to manage their own custom medical data alongside pre-loaded dummy data.

---

## ✨ Key Features

### 🔒 Premium OTP-Based Subscription
- **Secure Authentication**: Replaced legacy username/password with a strict Mobile Number + OTP flow.
- **Subscription Model**: Premium access charged at just 2Tk/Day.
- **Seamless Session Management**: Managed via secure `SharedPreferences`.
- **Easy Opt-out**: Built-in unsubscription flow directly from the user profile.

### 👩‍⚕️ Find & Book Doctors
- **Dynamic Data Management**: Browse pre-loaded "dummy" doctors across various specialties (Cardiologist, Dietitian, Dentist, etc.).
- **User-Generated Content**: Add and manage your own custom doctors locally. Custom doctors automatically appear at the top of the search lists.
- **Offline Booking**: Schedule appointments quickly and securely, saved directly to the local database.

### 🔬 Arrange Lab Tests & Cart System
- **Pre-packaged Tests**: Access a variety of pre-defined lab test packages.
- **Custom Lab Tests**: Users can add their own personalized lab tests to the database.
- **Smart Cart Integration**: A fully functional cart system that seamlessly handles both hardcoded and user-added lab tests.

### 📊 BMI Calculator & Health Tracking
- Calculate Body Mass Index (BMI) and view health categories.
- Track recent BMI results directly from the user profile dashboard.

### 🚑 Emergency & Health Tips
- Quick access to emergency contacts and health articles.
- Localized notifications and reminders for upcoming appointments and tests.

---

## 🛠 Tech Stack

The MEDiCARE app is built using modern Android development practices:

| **Technology** | **Usage** |
| :--- | :--- |
| **Android SDK** | Core application framework. |
| **Java** | Primary programming language. |
| **Room Database (v4)** | Robust local data persistence, handling schema migrations and custom user data. |
| **Retrofit & OkHttp** | REST API communication with the PHP backend (for OTP and unsubscription). |
| **SharedPreferences** | Session management and lightweight local storage. |
| **XML & Material UI** | Glassmorphic, premium UI design with custom drawables and modern layouts. |

---

## 🚀 Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/tem-mahadi/MEDiCARE.git
   ```
2. **Open the Project:**
   Open the cloned directory in **Android Studio**.
3. **Build & Sync:**
   Allow Gradle to sync the dependencies. Ensure you have the latest Android SDK installed.
4. **Run the App:**
   Deploy to an emulator or a physical Android device (Minimum SDK 21+).

> **Note:** The backend API (`send_otp.php`, `verify_otp.php`, `unsubscribe.php`) must be accessible via the URL specified in `RetrofitClient.java` for the authentication flow to function correctly.

---

## 📱 Application Flow

1. **Landing/Subscription:** Users enter their mobile number to subscribe (2Tk/Day) and receive an OTP.
2. **Verification:** OTP is verified with the backend, granting access to the main application.
3. **Home Dashboard:** Access to Find Doctors, Lab Tests, Cart, Appointments, and BMI Calculator.
4. **Data Management:** Add custom doctors or lab tests via the Floating Action Buttons (FAB) in their respective screens.
5. **Profile & Settings:** View stats, upcoming appointments, and manage the subscription.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 📬 Contact

Developed and maintained by:

- **Name**: Hasan Al Mahadi
- **Email**: mahadi4uruetcse21@gmail.com
- **GitHub**: [tem-mahadi](https://github.com/tem-mahadi)

---

*"Simplifying healthcare, one tap at a time."*
