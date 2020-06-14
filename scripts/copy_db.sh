#!/bin/bash

mysqldump -h codingmanager.com -u admin -p${CODING_MANAGER_PWD} codingmanager  > codingmanager_backup.sql

mysql -h codingmanager.com -u admin -p${CODING_MANAGER_PWD} -e "DROP DATABASE IF EXISTS test_codingmanager;"

mysqladmin -h codingmanager.com -u admin -p${CODING_MANAGER_PWD} create test_codingmanager

mysql -h codingmanager.com -u admin -p${CODING_MANAGER_PWD} test_codingmanager < codingmanager_backup.sql

rm codingmanager_backup.sql