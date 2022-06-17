INSERT INTO user (username, email, phone_num, password, ev, register_date,gender) VALUES
	("teacherM", "teacherM@qq.com", "13925677739", "teacher1234", 0, CURRENT_TIMESTAMP(),"man"),
	("taM", "taM@qq.com", "17725677739", "ta1234", 0, CURRENT_TIMESTAMP(),"man"),
	("tuna", "tuna@qq.com", "18925677739", "pass1234", 0, CURRENT_TIMESTAMP(),"man"),
	("chips", "chips@qq.com", "19946752244", "pass1234", 0, CURRENT_TIMESTAMP(),"man"),
	("student1", "student1@qq.com", "19943545344", "pass1234", 0, CURRENT_TIMESTAMP(),"man"),
	("student2", "student2@qq.com", "13494675221", "pass1234", 0, CURRENT_TIMESTAMP(),"man");
INSERT INTO v_class (name, course_name) VALUES
	("class1", "Advanced Web"),
	("class2", "Computer Graphics"),
	("class3", "GAMES101"),
	("class4", "GAMES202"),
	("class5", "RayTracing in One Weekend");
INSERT INTO task (name, description, ev, publisher_id, optional, team_size, ddl, validity) VALUES
	("Web3d PJ", "virtual study community using web3d html5...", 3, 2, 1, 4, TIMESTAMPADD(MONTH,1,CURRENT_TIMESTAMP), 1),# 3ev 必修 4人 审核通过
	("lab2", "", 2, 3, 1, 4, CURRENT_TIMESTAMP(), 2),# 2ev 必修 4人 已过期
	("assignment7", "monte carlo path tracing...", 4, 3, 1, 1, TIMESTAMPADD(WEEK,2,CURRENT_TIMESTAMP), 0),# 4ev 必修 1人 未审核
	("assignment6", "whitted-style ray tracing...", 3, 3, 1, 1, CURRENT_TIMESTAMP(), 2),# 3ev 必修 1人 已过期
	("assignment0", "environment config...", 0, 3, 0, 1, TIMESTAMPADD(WEEK,1,CURRENT_TIMESTAMP), 1);# 0ev 选修 1人 审核通过
INSERT INTO class_task(class_id, task_id) VALUES
	(1, 1),
	(1, 2),
	(3, 3),
	(3, 4),
	(5, 5);
	INSERT INTO accept(user_id, task_id) VALUES
	(3, 3),
	(3, 4),
	(3, 5),
	(4, 5);
	INSERT INTO v_group(task_id, process) VALUES
	(1, 3);
	INSERT INTO in_group(user_id, group_id) VALUES
	(3, 1),
	(4, 1),
	(5, 1);
	
	