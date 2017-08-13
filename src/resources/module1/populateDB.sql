use developers_db;

INSERT INTO `companies` VALUES (1,'Google'),(2,'Hooly'),(3,'FaceBook'),(4,'Apple');

INSERT INTO `customers` VALUES (1,'Goverment'),(2,'NASA'),(3,'Bruce Willis'),(4,'PENTAGON');

INSERT INTO `developers` VALUES (1,'Boris','Brown'),(2,'Igor','Polos'),(3,'Benedict','Arnold'),(4,'Helen','Siera'),(5,'Victoria','Brovar'),(6,'Dzan','YAng'),(7,'Robert','Wilson');

INSERT INTO `projects` VALUES (1,'Avalanchy'),(2,'Grouble '),(3,'SetTrans'),(4,'GlobalSfere'),(5,'TransStars'),(6,'StrongNut7');

INSERT INTO `skills` VALUES (1,'JavaSE'),(2,'JavaEE'),(3,'C++'),(4,'C#'),(5,'Python '),(6,'MySQL'),(7,'Java'),(8,'Hybernate');

INSERT INTO `developers_has_skills` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(1,7);

INSERT INTO `projects_has_developers` VALUES (1,1),(2,2),(3,3),(3,4),(3,5),(4,4),(5,5),(6,6);

INSERT INTO `companies_has_projects` VALUES (1,3),(1,5),(2,1),(2,2),(3,4),(3,6);

INSERT INTO `companies_has_developers` VALUES (1,1),(1,2),(1,3),(3,1),(3,4),(3,5);

INSERT INTO `projects_has_customers` VALUES (1,1),(1,2),(3,3),(3,1),(4,4);