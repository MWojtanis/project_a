# Wymagane
mysql (innoDB)  
docker-compose -> https://docs.docker.com/compose/ 

# Mysql
W celu uruchomienia bazy lokalnej z już przygotowanymi tabelami nalezy uruchomić w tym folderze z linii poleceń:  
 "docker-compose up"  
  
Sprawdzanie bazy danych z konsoli, wymagany jest wgranie mysql:   
"  
mysql -P 3306 --protocol=tcp -u root --password=abc123321  
use MW_DATABASE;  
select * from MW_TABLE;  
  
"  

Usunięcie:  
nazwa_volumenu - nazwa folderu i volumenu podzielone podłogą - mysql_MW
nazwa_kontenera - nazwa folderu i _db_1 - mysql_db_1  
 "docker container rm {nazwa_kontenera}"   
 "docker volume rm {nazwa_volumenu}"  
 


