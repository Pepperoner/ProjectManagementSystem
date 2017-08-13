 select projects.id, projects.project_name,
	    projects.cost, avg(developers.salary) average_salary
 from developers
		 inner join
	  projects_has_developers on developers.id = projects_has_developers.developers_id
		 inner join
	  projects on projects_has_developers.developers_id = projects.id
 group by projects.id
 order by projects.cost

