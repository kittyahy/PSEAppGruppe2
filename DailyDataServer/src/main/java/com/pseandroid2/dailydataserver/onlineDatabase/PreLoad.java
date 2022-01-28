/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
package com.pseandroid2.dailydataserver.onlineDatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
public class PreLoad {
    private static final Logger log = LoggerFactory.getLogger(PreLoad.class);

    @Bean
    CommandLineRunner initDatabase(ProjectParticipantService ppservice, DeltaOrganisationService deltaService) {

        LocalDateTime t = LocalDateTime.now();
        return args -> {
           log.info("New Project: "+ ppservice.addProject("Ella"));
           log.info("Add Participant: "+ppservice.addUser("Tom",1));
           log.info("Add Delta: "+ deltaService.saveDelta(1,"Ella","derCommand"));

           log.info("AddOldData: "+ deltaService.addOldDelta(1,"Tom","Ella","alter command",t,false));
           log.info("GetOldDelta: "+ deltaService.getOldDelta(1,"Ella"));
            log.info("GetNewDelta: "+deltaService.getNewDelta(1,"Tom"));
            log.info("GetOldDelta: "+ deltaService.getOldDelta(1,"Ella"));

        };
    }
}
