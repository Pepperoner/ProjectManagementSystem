select companies.company_name, customers.customer_name, min(projects.cost) as profit
from projects
		join
	 customers on projects.id = customers.id
		join
	 companies on projects.id = companies.id
group by (projects.id);