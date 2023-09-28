
import models.Appointments;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class AppointmentOverlapTest {


        @Test
        public void testNoOverlap() {
            // Create a sample appointment for testing
            Appointments appointment = new Appointments();
            appointment.setCUSTId(1);
            appointment.setApptDate(LocalDate.of(2023, 6, 1));
            appointment.setApptStartTime(LocalTime.of(10, 0));
            appointment.setApptEndTime(LocalTime.of(11, 0));

            // Create a sample appointment database with no existing appointments
            List<Appointments> existingAppointments = new ArrayList<>();

            // Create an instance of the class that contains the appointment overlap logic
            Appointments overlapChecker = new Appointments();

            // Set the desired start and end times for the appointment being checked
            LocalDateTime myZDTST = LocalDateTime.of(2023, 6, 1, 9, 0);
            LocalDateTime myZDTET = LocalDateTime.of(2023, 6, 1, 10, 0);

            // Call the method that checks for overlapping appointments
            boolean isOverlap = overlapChecker.checkAppointmentOverlap(existingAppointments, appointment, myZDTST, myZDTET);

            // Assert that there is no overlap
            assertFalse(isOverlap);
        }

        @Test
        public void testOverlap() {
            // Create a sample appointment for testing
            Appointments appointment = new Appointments();
            appointment.setCUSTId(1);
            appointment.setApptDate(LocalDate.of(2023, 6, 1));
            appointment.setApptStartTime(LocalTime.of(10, 0));
            appointment.setApptEndTime(LocalTime.of(11, 0));

            // Create a sample appointment database with an existing overlapping appointment
            List<Appointments> existingAppointments = new ArrayList<>();
            Appointments existingAppointment = new Appointments();
            existingAppointment.setCUSTId(1);
            existingAppointment.setApptDate(LocalDate.of(2023, 6, 1));
            existingAppointment.setApptStartTime(LocalTime.of(9, 30));
            existingAppointment.setApptEndTime(LocalTime.of(10, 30));
            existingAppointments.add(existingAppointment);

            // Create an instance of the class that contains the appointment overlap logic
            Appointments  overlapChecker = new Appointments();

            // Set the desired start and end times for the appointment being checked
            LocalDateTime myZDTST = LocalDateTime.of(2023, 6, 1, 9, 30);
            LocalDateTime myZDTET = LocalDateTime.of(2023, 6, 1, 10, 30);

            // Call the method that checks for overlapping appointments
            boolean isOverlap = overlapChecker.checkAppointmentOverlap(existingAppointments, appointment, myZDTST, myZDTET);

            // Assert that there is an overlap
            assertFalse(isOverlap);
        }
    }