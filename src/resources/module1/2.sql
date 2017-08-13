-- select project_name, sum(developers.salary) as MostExpensiveProject, 
-- projects.id from developers_db.projects, developers_db.developers 
-- where projects.id = developers.id 
-- group by projects.id
-- order by sum(developers.salary) desc limit 1;
-- select * from developers.salary

select projects.id, projects.project_name, sum(developers.salary) as MostExpensiveProject
from developers 
		 inner join 
     projects_has_developers on developers.id = projects_has_developers.developers_id
		 inner join
	  projects on projects_has_developers.projects_id = projects.id
 group by projects.id
 order by sum(developers.salary) desc limit 1;

-- SELECT  projects.id, projects.project_name, sum(developers.salary) as MostExpensiveProject
-- from projects INNER JOIN projects_has_developers INNER JOIN developers
-- WHERE projects.id = projects_has_developers.projects_id AND projects_has_developers.developers_id = developers.id
-- GROUP BY projects.id
-- ORDER BY sum(developers.salary) DESC LIMIT 1
