-- SELECT SUM(salary) AS SUM_salary_of_Java_Developers 
-- FROM developers_db.developers 
-- WHERE id IN (SELECT developers_id FROM developers_db.developers_has_skills WHERE skills_id IN (SELECT id FROM developers_db.skills WHERE skills = 'Java'));

select skills.skills, sum(salary) as total_salary
from developers
		inner join 
     developers_has_skills on developers.id = developers_has_skills.developers_id
		inner join
	 skills on developers_has_skills.skills_id = skills.id
where skills.skills = 'Java'