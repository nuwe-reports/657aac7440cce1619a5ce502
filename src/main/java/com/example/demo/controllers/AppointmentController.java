package com.example.demo.controllers;

import com.example.demo.repositories.*;
import com.example.demo.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador para gestionar las operaciones relacionadas con las citas médicas.
 */
@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    /**
     * Obtiene todas las citas médicas.
     * @return ResponseEntity con la lista de citas médicas o NO_CONTENT si no hay citas.
     */
    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> appointments = new ArrayList<>();

        appointmentRepository.findAll().forEach(appointments::add);

        if (appointments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     * Obtiene una cita médica por su ID.
     * @param id ID de la cita médica.
     * @return ResponseEntity con la cita médica encontrada o NOT_FOUND si no se encuentra.
     */
    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()){
            return new ResponseEntity<>(appointment.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crea una nueva cita médica.
     * @param appointment Datos de la cita médica a crear.
     * @return ResponseEntity con el estado de la operación.
     */
    @PostMapping("/appointment")
    public ResponseEntity<List<Appointment>> createAppointment(@RequestBody Appointment appointment){
        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();
        Room room = appointment.getRoom();
        LocalDateTime startsAt = appointment.getStartsAt();
        LocalDateTime finishesAt = appointment.getFinishesAt();

        // Se valida que no exista campos nulos.
        if (doctor == null || patient == null || room == null || startsAt == null || finishesAt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Se valida que startsAt sea anterior a finishesAt.
        if (startsAt.equals(finishesAt) || startsAt.isAfter(finishesAt)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Se valida solapamiento de citas.
        List<Appointment> existingAppointments = appointmentRepository.findAll();

        for (Appointment existingAppointment : existingAppointments) {
            if (existingAppointment.overlaps(appointment)) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        // Se guarda la cita en la base de datos.
        try {
            appointmentRepository.save(appointment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Elimina una cita médica por su ID.
     * @param id ID de la cita médica a eliminar.
     * @return ResponseEntity con el estado de la operación.
     */
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id){

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (!appointment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appointmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
        
    }

    /**
     * Elimina todas las citas médicas.
     * @return ResponseEntity con el estado de la operación.
     */
    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments(){
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
