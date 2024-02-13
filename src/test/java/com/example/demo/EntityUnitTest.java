package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	private Doctor doctor;
	private Patient patient;
    private Room room;

    private Appointment appointment1;

    private LocalDateTime startsAt1;
    private LocalDateTime startsAt2;
    private LocalDateTime startsAt3;
    private LocalDateTime finishesAt1;
    private LocalDateTime finishesAt2;
    private LocalDateTime finishesAt3;

    /*     Test para la entidad Appointment     */
    // Test para validar el solapamiento de citas de la entidad appointment.
    @Test
    public void testAppointmentOverlaps() {
        room = new Room("Room1");
        patient = new Patient("Juan", "Perez", 62, "mi-email@email.com");
        doctor = new Doctor("Antonio", "Mendoza", 33, "doctor@email.com");

        startsAt1 = LocalDateTime.of(2024, 2, 10, 10, 0);
        finishesAt1 = LocalDateTime.of(2024, 2, 10, 12, 0);
        appointment1 = new Appointment(patient, doctor, room, startsAt1, finishesAt1);

        startsAt2 = LocalDateTime.of(2024, 2, 10, 11, 0);
        finishesAt2 = LocalDateTime.of(2024, 2, 10, 13, 0);
        Appointment overlappingAppointment = new Appointment(patient, doctor, room, startsAt2, finishesAt2);

        startsAt3 = LocalDateTime.of(2024, 2, 10, 14, 0);
        finishesAt3 = LocalDateTime.of(2024, 2, 10, 16, 0);
        Appointment nonOverlappingAppointment = new Appointment(patient, doctor, room, startsAt3, finishesAt3);

        Assertions.assertTrue(appointment1.overlaps(overlappingAppointment));
        Assertions.assertFalse(appointment1.overlaps(nonOverlappingAppointment));
    }

    // Test para validar los metodos getters y setters de la entidad appointment.
    @Test
    public void testAppointmentGettersAndSetters() {
        room = new Room("Room1");
        patient = new Patient("Juan", "Perez", 62, "mi-email@email.com");
        doctor = new Doctor("Antonio", "Mendoza", 33, "doctor@email.com");

        startsAt1 = LocalDateTime.of(2024, 2, 10, 10, 0);
        finishesAt1 = LocalDateTime.of(2024, 2, 10, 12, 0);
        appointment1 = new Appointment();

        appointment1.setRoom(room);
        appointment1.setPatient(patient);
        appointment1.setDoctor(doctor);
        appointment1.setStartsAt(startsAt1);
        appointment1.setFinishesAt(finishesAt1);

        assertEquals(room, appointment1.getRoom());
        assertEquals(patient, appointment1.getPatient());
        assertEquals(doctor, appointment1.getDoctor());
        assertEquals(startsAt1, appointment1.getStartsAt());
        assertEquals(finishesAt1, appointment1.getFinishesAt());
    }


    /*     Tests para la entidad Doctor     */
    // Test para validar el constructor y metodos getters de la entidad doctor.
    @Test
    public void testDoctorConstructorAndGetters() {
        String firstName = "Juan";
        String lastName = "Perez";
        int age = 35;
        String email = "juan.perez@email.com";

        doctor = new Doctor(firstName, lastName, age, email);

        assertEquals(firstName, doctor.getFirstName());
        assertEquals(lastName, doctor.getLastName());
        assertEquals(age, doctor.getAge());
        assertEquals(email, doctor.getEmail());
    }

    // Test para validar constructor por defecto y metodos setters de la entidad Doctor.
    @Test
    public void testDoctorDefaultConstructorAndSetters() {
        doctor = new Doctor();

        String firstName = "Maria";
        String lastName = "Torres";
        int age = 40;
        String email = "maria.torres@email.com";

        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setAge(age);
        doctor.setEmail(email);

        assertEquals(firstName, doctor.getFirstName());
        assertEquals(lastName, doctor.getLastName());
        assertEquals(age, doctor.getAge());
        assertEquals(email, doctor.getEmail());
    }

    // Test para validar el id de la entidad Doctor.
    @Test
    public void testDoctorId() {
        doctor = new Doctor();

        long id = 123;
        doctor.setId(id);

        assertEquals(id, doctor.getId());
    }


    /*     Tests para la entidad Patient     */
    // Test para validar el constructor y los metodos getters de la entidad Patient.
    @Test
    public void testPatientConstructorAndGetters() {
        String firstName = "Antonio";
        String lastName = "Mendoza";
        int age = 40;
        String email = "example@email.com";

        patient = new Patient(firstName, lastName, age, email);

        assertEquals(firstName, patient.getFirstName());
        assertEquals(lastName, patient.getLastName());
        assertEquals(age, patient.getAge());
        assertEquals(email, patient.getEmail());
    }

    // Test para validar el constructor por defecto y los metodos setters de la entidad Patient.
    @Test
    public void testPatientDefaultConstructorAndSetters() {
        patient = new Patient();

        String firstName = "Clara";
        String lastName = "GC";
        int age = 40;
        String email = "clara.gc@example.com";

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setEmail(email);

        assertEquals(firstName, patient.getFirstName());
        assertEquals(lastName, patient.getLastName());
        assertEquals(age, patient.getAge());
        assertEquals(email, patient.getEmail());
    }

    // Test para validar el id de la entidad Patient.
    @Test
    public void testPatientId() {
        patient = new Patient();

        long id = 123;
        patient.setId(id);

        assertEquals(id, patient.getId());
    }


    /*     Tests para la entidad Room     */
    // Test para validar el constructor y los metodos getters de la entidad Room.
    @Test
    public void testRoomConstructorAndGetters() {
        String roomName = "Room1";

        room = new Room(roomName);

        assertEquals(roomName, room.getRoomName());
    }


    /*     Test para la entidad Person     */
    // Test para validar el constructor y los metodos getters de la entidad Person.
    @Test
    public void testPersonConstructorAndGetters() {
        String firstName = "Antonio";
        String lastName = "Mendoza";
        int age = 40;
        String email = "example@email.com";

        Person person = new Person(firstName, lastName, age, email);

        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(age, person.getAge());
        assertEquals(email, person.getEmail());
    }

    // Test para validar el constructor por defecto y los metodos setters de la entidad Person
    @Test
    public void testPersonDefaultConstructorAndSetters() {
        Person person = new Person();

        String firstName = "Clara";
        String lastName = "GC";
        int age = 40;
        String email = "clara.gc@example.com";

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);
        person.setEmail(email);

        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(age, person.getAge());
        assertEquals(email, person.getEmail());
    }
}
