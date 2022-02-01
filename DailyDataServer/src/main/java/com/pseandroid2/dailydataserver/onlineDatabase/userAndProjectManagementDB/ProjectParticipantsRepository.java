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
package com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for ProjectParticipant.
 * <p>
 * It defines methods to operate on the table FetchRequestsTable.
 */
@Repository
public interface ProjectParticipantsRepository extends JpaRepository<ProjectParticipant, ProjectParticipantID> {

    /**
     * Returns the amount of ProjectParticipants who participate in the recommended project.
     *
     * @param project the recommended project, from which the participants should be count.
     * @return the amount of participants who participate currently in the project.
     */
    long countByProject(long project);

    /**
     * Finds and returns all ProjectParticipants, who participate in the given project. The list is sorted by the
     * numberOfJoin, that the latest join is the last entry in the list.
     *
     * @param project the project from which the participants are recommended.
     * @return the recommended and sorted list.
     */
    List<ProjectParticipant> findByProjectOrderByNumberOfJoinAsc(long project);

    /**
     * Returns all ProjectParticipants from the recommended Project.
     *
     * @param projectId the project, where the participants participate.
     * @return the recommended list.
     */
    List<ProjectParticipant> findByProject(long projectId);

    /**
     * Returns all ProjectParticipants from a project, with a defined role.
     *
     * @param project the recommended project.
     * @param role    the role, which the participants should have.
     * @return the recommended list.
     */
    ProjectParticipant findByProjectAndRoleIs(long project, Role role);
}
