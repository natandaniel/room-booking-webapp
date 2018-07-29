package com.natandanielapps.consensysbooking.events;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.natandanielapps.consensysbooking.model.Room;

@Component
@RepositoryEventHandler(Room.class) 
public class RoomEventHandler {
	
    
   @HandleBeforeCreate
   public void handleRoomBeforeCreate(Room room){
       System.out.println("Inside Room Before Create....");
   }

   @HandleAfterCreate
   public void handleRoomAfterCreate(Room room){
       System.out.println("Inside Room After Create ....");
   }
   
   @HandleAfterSave
   public void handleRoomAfterSave(Room room){
       System.out.println("Inside Room After Save ....");
   }
   
}
