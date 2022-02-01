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
package com.pseandroid2.dailydataserver.postDatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for Templates, to operate on the Template_Table.
 */
@Repository
public interface TemplateRepository extends JpaRepository<Template, TemplateId> {

    /**
     * Returns a list of all Templates, which belongs to a given post
     *
     * @param post the post, to which the templates belong.
     * @return the recommended list of Deltas.
     */
    List<Template> findByPost(int post);

    /**
     * Returns the project template for a post. Every post only has one projectTemplate.
     *
     * @param postID the post, from which the project template is recommended.
     * @return the project template for the given post.
     */
    @Query(value = "select t from Template t where t.post = ?1 and t.isProjectTemplate = true")
    Template findByPostAndIsProjectTemplateIsTrue(int postID);
}