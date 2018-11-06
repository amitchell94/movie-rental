# Movie Rental System

An example of a move rental system which uses the system console as the UI.

It allows users to create a user, select a movie to rent and return rented movies. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

In order to execute this program you will need to install the following:
* Java JDK
* MySQL

### Installing
To get the development environment up and running, you will first need to create the database.

This can be achieved by either importing the database schema, or manually using the terminal.
#### Setting up database by importing schema
Download the schema [blockbuster.sql](blockbuster.sql)

Import the schema into your MySQL server.

#### Setting up database using terminal

In your terminal login to your MySQL as a user with the appropriate permissions.

Then enter the following command to create your database.
```
CREATE DATABASE blockbuster;
```
Then use this new database
```
USE blockbuster;
```

You will need to create the following tables in this database:

A customer table

```
CREATE TABLE `customers` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(25) DEFAULT NULL,
  `c_address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
);
```

A movies table

```
CREATE TABLE `movies` (
  `m_id` int(10) NOT NULL AUTO_INCREMENT,
  `m_title` varchar(15) DEFAULT NULL,
  `m_actor` varchar(20) DEFAULT NULL,
  `m_year` year(4) DEFAULT NULL,
  `m_genre` varchar(15) DEFAULT NULL,
  `m_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`m_id`)
);

```
And a rentals table

```
CREATE TABLE `rentals` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_c_id` int(11) DEFAULT NULL,
  `r_m_id` int(11) DEFAULT NULL,
  `r_rental_date` date DEFAULT NULL,
  `r_return_date` date DEFAULT NULL,
  `r_cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`r_id`),
  KEY `fk_r_m_id` (`r_m_id`),
  KEY `fk_r_c_id` (`r_c_id`),
  CONSTRAINT `fk_r_c_id` FOREIGN KEY (`r_c_id`) REFERENCES `customers` (`c_id`),
  CONSTRAINT `fk_r_m_id` FOREIGN KEY (`r_m_id`) REFERENCES `movies` (`m_id`)
);

```

## Running the tests

No automated test included in this issue.


## Deployment

This project has yet to be deployed on a live system.

## Built With

* [Gradle](https://gradle.com/) - Dependency Management

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Andy Mitchell** - *Initial work* - [GitHub](https://github.com/amitchell94)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* CodingNomads for teaching me Java!
