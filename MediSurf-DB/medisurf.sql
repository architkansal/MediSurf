-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 27, 2017 at 11:03 PM
-- Server version: 5.5.49-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `medisurf`
--

-- --------------------------------------------------------

--
-- Table structure for table `alternatives`
--

CREATE TABLE IF NOT EXISTS `alternatives` (
  `original` varchar(200) NOT NULL,
  `alternative` varchar(200) NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL,
  `time_value` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `alternatives`
--

INSERT INTO `alternatives` (`original`, `alternative`, `latitude`, `longitude`, `time_value`) VALUES
('dsoidd', 'discorb', '54.5341', '526.23', '2016-11-06 01:07:28'),
('discorb', 'Glubos', '17.9839378', '79.5330768', '2016-11-06 01:08:13'),
('liofen xl', ' Parafo', '17.9839378', '79.5330768', '2016-11-06 01:13:09'),
('defzar', ' laz', '17.9839378', '79.5330768', '2016-11-06 01:13:09'),
('triben-v', 'Clima Fort', '17.9839378', '79.5330768', '2016-11-06 01:13:09'),
('liofen xl', ' Spinofe', '17.9839378', '79.5330768', '2016-11-06 02:56:17'),
('defzar', ' Defza', '17.9839378', '79.5330768', '2016-11-06 02:56:17'),
('triben-v', ' Camyd', '17.9839378', '79.5330768', '2016-11-06 02:56:17'),
('discorb', ' Glubos', '17.9839378', '79.5330768', '2016-11-06 03:54:28'),
('Spinofen', 'Parafo', '17.9839378', '79.5330768', '2016-11-06 05:45:45'),
('Defnom', 'Defza', '17.9839378', '79.5330768', '2016-11-06 05:45:45'),
('Triben-v', 'Camyd', '17.9839378', '79.5330768', '2016-11-06 05:45:45'),
('Spinofen', ' Parafo', '17.983673', '79.5332525', '2016-11-06 06:20:43'),
('Defnom', ' Defza', '17.983673', '79.5332525', '2016-11-06 06:20:43'),
('Triben-v', ' Triben-', '17.983673', '79.5332525', '2016-11-06 06:20:43'),
('Triben-v', ' Clima Fort', '17.9827868', '79.5346138', '2016-11-07 15:38:16'),
('Spinofen', ' Parafo', '17.9827868', '79.5346138', '2016-11-07 15:38:16'),
('Defnom', ' Defno', '17.9827868', '79.5346138', '2016-11-07 15:38:16'),
('Glubose', ' Glubos', '17.9830754', '79.5349884', '2016-11-12 18:24:27'),
('Clima forte', ' Camyd', '17.9830754', '79.5349884', '2016-11-12 18:24:27'),
('Spinofen', ' Parafo', '17.9830754', '79.5349884', '2016-11-12 18:24:27'),
('Spinofen', ' Parafo', '17.9891308', '79.540012', '2017-01-16 10:51:40'),
('Triben-v', ' Clima Fort', '17.9891308', '79.540012', '2017-01-16 10:51:40'),
('Clima forte', ' Camyd', '17.9891308', '79.540012', '2017-01-16 10:51:40');

-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

CREATE TABLE IF NOT EXISTS `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `generic_salt` varchar(200) NOT NULL,
  `brand_name` varchar(200) NOT NULL,
  `price` float NOT NULL,
  `description` text NOT NULL,
  `type` varchar(100) NOT NULL,
  `mg_ml` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `medicine`
--

INSERT INTO `medicine` (`id`, `name`, `generic_salt`, `brand_name`, `price`, `description`, `type`, `mg_ml`) VALUES
(1, 'Discorb', 'Acarbose', 'elder pharmaceuticals pvt ltd', 7.5, '\r\nAcarbose prevents the action of certain enzymes in the digestive tract that break down carbohydrates to release glucose, which is subsequently absorbed in the blood. Thus, it controls the rise of blood glucose level after a meal. ', 'tablet', 50),
(2, 'Acarex', 'Acarbose', 'invision medi sciences', 5.5, '\r\nAcarbose prevents the action of certain enzymes in the digestive tract that break down carbohydrates to release glucose, which is subsequently absorbed in the blood. Thus, it controls the rise of blood glucose level after a meal. \r\n', 'tablet', 25),
(3, 'Glubose', 'Acarbose', 'otsira aristo pharmaceuticals pvt ltd', 3.6, '\r\nAcarbose prevents the action of certain enzymes in the digestive tract that break down carbohydrates to release glucose, which is subsequently absorbed in the blood. Thus, it controls the rise of blood glucose level after a meal. \r\n', 'tablet', 25),
(4, 'Liofen XL', 'Baclofen', 'sun pharmaceutical industries ltd', 28, 'This medication is a derivative of gamma-aminobutyric acid (GABA), prescribed for severe chronic spasticity. It also relieves pain and improves muscle movement.\r\n\r\n\r\n\r\n\r\n', 'tablet', 30),
(5, 'Parafon', 'Baclofen', 'johnson & johnson', 5.03, 'This medication is a derivative of gamma-aminobutyric acid (GABA), prescribed for severe chronic spasticity. It also relieves pain and improves muscle movement', 'tablet', 500),
(6, 'Spinofen', 'Baclofen', 'Chemo Biological', 12.2, 'This medication is a derivative of gamma-aminobutyric acid (GABA), prescribed for severe chronic spasticity. It also relieves pain and improves muscle movement', 'tablet', 25),
(7, 'Defnom', 'Deflazacort', 'Acinom healthcare', 8, '\r\nThis medication is a glucocorticoid, prescribed for anti-inflammatory conditions, and used as an immunosuppressant. It is a prodrug.', 'tablet', 6),
(8, 'laza', 'Deflazacort', 'Vilberry healthcare', 7.65, 'This medication is a glucocorticoid, prescribed for anti-inflammatory conditions, and used as an immunosuppressant. It is a prodrug.', 'tablet', 6),
(9, 'Defzar', 'Deflazacort', 'Argos Healthcare pvt ltd', 35, 'This medication is a glucocorticoid, prescribed for anti-inflammatory conditions, and used as an immunosuppressant. It is a prodrug.', 'tablet', 30),
(10, 'Triben-V', 'Clindamycin', 'jenburkt', 113, '\r\nThis medication is an antibiotic, prescribed for certain types of bacterial infections, including infections of the lungs, skin, blood, female reproductive organs and internal organs.  It slows or stops the growth of bacteria.', 'tablet', 100),
(11, 'Clima Forte', 'Clindamycin', 'Dewcare', 14.2, '\r\nThis medication is an antibiotic, prescribed for certain types of bacterial infections, including infections of the lungs, skin, blood, female reproductive organs and internal organs.  It slows or stops the growth of bacteria.', 'tablet', 100),
(12, 'Camyda', 'Clindamycin', 'Wallace', 15, '\r\nThis medication is an antibiotic, prescribed for certain types of bacterial infections, including infections of the lungs, skin, blood, female reproductive organs and internal organs.  It slows or stops the growth of bacteria.', 'tablet', 150);

-- --------------------------------------------------------

--
-- Table structure for table `price`
--

CREATE TABLE IF NOT EXISTS `price` (
  `original` int(11) NOT NULL,
  `altered` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `price`
--

INSERT INTO `price` (`original`, `altered`) VALUES
(0, 0),
(0, 0),
(0, 0),
(0, 0),
(924, 887),
(924, 887),
(924, 887),
(924, 887),
(924, 887),
(2198, 1351),
(776, 399),
(75, 72),
(457, 247),
(457, 376),
(457, 285),
(69, 13),
(309, 73);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
