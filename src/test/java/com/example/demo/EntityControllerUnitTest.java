
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Prueba que el controlador pueda devolver correctamente todos los doctores.
    @Test
    public void testGetAllDoctors() throws Exception {
        Doctor doctor = new Doctor("Juan", "perez", 35, "juan.perez@example.com");
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(doctors)));
    }

    // Prueba que el controlador pueda devolver correctamente un doctor por su ID.
    @Test
    public void testGetDoctorById() throws Exception {
        long id = 1;
        Doctor doctor = new Doctor("Juan", "perez", 35, "juan.perez@example.com");
        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));

        mockMvc.perform(get("/api/doctors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(doctor.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(doctor.getLastName()))
                .andExpect(jsonPath("$.age").value(doctor.getAge()))
                .andExpect(jsonPath("$.email").value(doctor.getEmail()));
    }

    // Prueba que el controlador pueda crear un doctor.
    @Test
    public void testCreateDoctor() throws Exception {
        Doctor doctor = new Doctor("Juan", "Perez", 42, "juanp@example.com");
        when(doctorRepository.save(any())).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Perez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(42))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("juanp@example.com"));
    }

    // Prueba que el controlador pueda eliminar un doctor por su ID.
    @Test
    public void testDeleteDoctor() throws Exception {
        long id = 1L;
        when(doctorRepository.findById(id)).thenReturn(Optional.of(new Doctor()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/doctors/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(doctorRepository, times(1)).deleteById(id);
    }

    // Prueba que el controlador pueda eliminar todos los doctores.
    @Test
    public void testDeleteAllDoctors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/doctors"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(doctorRepository, times(1)).deleteAll();
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Prueba que el controlador pueda devolver correctamente todos los pacientes.
    @Test
    public void testGetAllPatients() throws Exception {
        Patient patient = new Patient("Juan", "Perez", 35, "juan.perez@example.com");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(patients)));
    }

    // Prueba que el controlador pueda devolver correctamente un paciente por su ID.
    @Test
    public void testGetPatientById() throws Exception {
        long id = 1;
        Patient patient = new Patient("Juan", "Perez", 35, "juan.perez@example.com");
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patient.getLastName()))
                .andExpect(jsonPath("$.age").value(patient.getAge()))
                .andExpect(jsonPath("$.email").value(patient.getEmail()));
    }

    // Prueba que el controlador pueda crear un nuevo paciente.
    @Test
    public void testCreatePatient() throws Exception {
        Patient patient = new Patient("Alicia", "Gutierrez", 38, "AliciaG@example.com");
        when(patientRepository.save(any())).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Alicia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gutierrez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(38))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("AliciaG@example.com"));
    }

    // Prueba que el controlador pueda eliminar un paciente por su ID.
    @Test
    public void testDeletePatient() throws Exception {
        long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.of(new Patient()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(patientRepository, times(1)).deleteById(id);
    }

    // Prueba que el controlador pueda eliminar todos los pacientes.
    @Test
    public void testDeleteAllPatients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(patientRepository, times(1)).deleteAll();
    }
}

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Prueba que el controlador pueda devolver correctamente todas las habitaciones.
    @Test
    public void testGetAllRooms() throws Exception {
        Room room1 = new Room("Room1");
        Room room2 = new Room("Room2");
        List<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].roomName").value("Room1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].roomName").value("Room2"));
    }

    // Prueba que el controlador pueda devolver correctamente una habitación por su nombre.
    @Test
    public void testGetRoomByRoomName() throws Exception {
        String roomName = "Room1";
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(new Room(roomName)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/{roomName}", roomName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomName").value(roomName));
    }

    // Prueba que el controlador pueda crear una nueva habitación.
    @Test
    public void testCreateRoom() throws Exception {
        Room room = new Room("Room3");
        when(roomRepository.save(any())).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomName").value("Room3"));
    }

    // Prueba que el controlador pueda eliminar una habitación por su nombre.
    @Test
    public void testDeleteRoom() throws Exception {
        String roomName = "Room1";
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(new Room()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rooms/{roomName}", roomName))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(roomRepository, times(1)).deleteByRoomName(roomName);
    }

    // Prueba que el controlador pueda eliminar todas las habitaciones.
    @Test
    public void testDeleteAllRooms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rooms"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(roomRepository, times(1)).deleteAll();
    }

}
