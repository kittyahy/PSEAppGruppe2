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
package com.pseandroid2.dailydataserver;

import com.pseandroid2.dailydataserver.onlineDatabase.AccessToProjectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * configurations for the Interceptors.
 * This is the point, where Spring know which request should be intercepted.
 */
@Component
public class GeneralConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AccessToProjectInterceptor accessToProjectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/greet");
        registry.addInterceptor(accessToProjectInterceptor).addPathPatterns("/OnlineDatabase/**").excludePathPatterns("/OnlineDatabase/Delta/time");
    }

    //Firebase initalisieren
    // FIREBASE_DATABASE_URL braucht eigentlich eine richtige URL, aber wie und woher etc...?
   /* @Primary
    @Bean
    public void firebaseInit() throws IOException{
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.getApplicationDefault()).setDatabaseUrl("FIREBASE_DATABASE_URL").build();
        if (FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }
    }*/
}
