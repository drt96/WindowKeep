package com.windowkeep;

import java.util.List;

/*
 A class to hold a list of Calender appointments
 */
public class CalenderData {
   private List <Appointment> appointments;

   public CalenderData(List<Appointment> appointments) {
      this.appointments = appointments;
   }

   @Override
   public String toString() {
      return "Calender Data" +
              " Appointments: " + appointments.toString();
   }
}
