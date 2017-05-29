-- --------------------------------------
-- Inserción de los Usuarios de prueba --
INSERT INTO
`qvt-db`.`Usuario` (`username`, `password`, `nombres`, `apellidos`, `email`)
VALUES
('admin', 'admin', 'Admin', 'Test', 'admin@test.com'),
('jpsalas1', 'jpsalas1', 'Juan Pedro', 'Pérez Salas', 'jp.perez.salas@gmail.com');

-- --------------------------------------
-- Inserción de los Canales Nacionales --
INSERT INTO
`qvt-db`.`Canal` (`nombre`)
VALUES
('Canal 13'),
('Chilevision'),
('MEGA'),
('TVN');

-- ---------------------------------------
-- Inserción de Categorias de Programas --
INSERT INTO
`qvt-db`.`Categoria` ( `nombre` )
VALUES
('Matinal'),
('Serie'),
('Estelar'),
('Reality'),
('Serie Legal'),
('Serie Cocina'),
('Serie Nacional');

-- -------------------------------------------------------------------------------
-- Inserción de algunos programas transmitidos por sus correspondientes canales --
-- CANAL 13 --
INSERT INTO `qvt-db`.`Programa`
(`canal_id`, `usuario_id`, `nombre`,`descripcion`, `inicio`, `termino`)
VALUES
(1, 1, 'Bienvenidos', "El entretenimiento, noticias de farándula, recetas de cocina y la información necesaria para comenzar el día. Presentadores Tonka Tomicic y Martín Cárcamo.",'08:00', '13:30'),
(1, 1, 'Paramparça',"La información del programa no fue provista por el proveedor de la señal.", '15:30', '17:00'),
(1, 1, 'Entre dos amores',"La historia de una joven llamada Neriman quien perdió de niña a su madre y vive en un barrio humilde de Estambul, junto a su tía y a su conservador padre. Desde pequeña ha estado enamorada de su amigo Sinasi, quien corresponde estos sentimientos.", '17:00', '18:00'),
(1, 1, 'Amor a segunda vista',"Fatih, es un joven millonario que no quiere que su familia lo obligue a casarse con Irem y Zeynep, necesita tiempo para confesar a su familia que es madre soltera. Ellos se encuentran en un vuelo a Turquía y fingirán estar casados.", '18:00', '19:10'),
(1, 1, 'La pequeña casa en la pradera',"Una familia de pioneros lucha por sobrevivir en Minnesota alrededor del año 1870.", '19:10', '21:00'),
(1, 1, 'Vertigo',"Un programa de entretenimiento, concursos y mucha diversión donde el público televidente escoge mediante votación al mejor invitado de la noche.", '22:35', '01:00'),
(1, 1, 'Kosem',"Tomando el nombre de Kosem, que significa líder y guía Anastasia se convertirá en una de las mujeres más poderosas en la historia del Imperio Otomano.", '22:35', '00:15'),
(1, 1, 'MasterChef Chile',"Un programa que se ha realizado en más de 145 países presenta la versión chilena, donde 15 chefs aficionados y otros seleccionados entre miles competirán por cumplir el sueño de convertirse en el primer MasterChef de Chile.", '23:30', '01:30'),

-- CHILEVISION --
(2, 2, 'La mañana',"Informativo Diario.", '08:00', '13:30'),
(2, 2, 'La jueza',"Carmen Gloria Arroyo analiza y resuelve conflictos legales.", '15:30', '17:30'),
(2, 2, 'Lo que callamos las mujeres',"Dramatización de las historias que viven las mujeres en su entorno social.", '17:30', '18:30'),
(2, 2, 'Vidas en riesgo',"Los dramas que se viven en el servicio de urgencia de un hospital, las vidas de los médicos su perfil psicológico y cómo se enfrentan a sus casos.", '18:30', '19:30'),
(2, 2, 'Caso cerrado',"La abogada Ana María Polo intenta resolver los conflictos y las disputas entre las partes involucradas, los casos que son presentados ante ella son variados, en un escenario que hace las veces de juzgado.", '19:30', '21:00'),
(2, 2, 'Espías del amor',"La información del programa no fue provista por el proveedor de la señal.", '22:30', '00:00'),
(2, 2, 'Primer plano', "Toda la información sobre el mundo de la farándula de la televisión chilena.",'22:30', '00:30'),

-- MEGA --
(3, 1, 'Mucho gusto',"Un programa que se encarga de llevar información y entretenimiento de una manera muy alegre a los hogares chilenos.", '08:00', '13:00'),
(3, 1, 'Amanda',"Amanda buscará librarse de sus miedos y vengarse de la persona que arruinó su vida. Sin embargo, al intentarlo se enamorará de una persona no correspondida.", '15:15', '16:30'),
(3, 1, 'Sevda',"La información del programa no fue provista por el proveedor de la señal.", '16:30', '17:15'),
(3, 1, 'El regreso de Lucas', "Luego de 20 años de la desaparición de Lucas, llega a manos de la madre una foto de su hijo. Una nueva pista que renueva las esperanzas de Elena de hallar a su hijo. Hasta que un día, un joven se presenta diciendo ser Lucas. ¿Será él su hijo perdido?",'17:15', '18:00'),
(3, 1, 'El secreto de Feriha',"Feriha Yilmaz es una chica de orígenes humildes que vive en el sótano de un lujoso edificio. La joven obtiene una beca completa para estudiar en una universidad privada y rápidamente pasa a ser el centro de atención, incluso del playboy de la universidad. ", '18:00', '20:15'),
(3, 1, 'Tranquilo papá',"La información del programa no fue provista por el proveedor de la señal.", '20:15', '21:00'),
(3, 1, 'Perdona nuestros pecados',"Perdona nuestros pecados es una telenovela chilena de horario nocturno producida y transmitida por Mega. Escrita por Pablo Illanes y Josefina Fernández, y dirigida por Nicolás Alemparte, bajo la supervisión general de María Eugenia Rencoret.", '22:30', '23:30'),
(3, 1, 'Morandé con compañia',"Un momento de entretención junto a Kike Morandé y sus bellas modelos.", '22:30', '00:45'),
(3, 1, 'Doble tentación',"Reality show donde se pondrá a prueba la fidelidad de siete parejas que, expuestos a una tentación encarnada por espectaculares solteros y solteras, enfrentarán sus verdaderos sentimientos revelando pasiones, fortalezas y debilidades.", '23:30', '01:00'),
(3, 1, 'Más vale tarde',"Un programa de entrevistas a distintos artistas del espectáculo quien junto a los invitados opinan sobre las noticias nacionales e internacionales.", '01:00', '02:00'),

-- TVN --
(4, 2, 'Muy buenos días',"Servicios, conversación, humor y la mejor compañía para empezar cada día. ", '08:00', '13:00'),
(4, 2, 'Elif', "La criada de una familia se enamora del hijo favorito, queda embarazada y la madre del chico la envía lejos, nace Elif y su padre, quien no sabe de su existencia, se casa con otra, en tanto que su madre se casa con un hombre malvado.",'15:30', '17:15'),
(4, 2, 'Saras y Kumud',"Un hombre es forzado por su padre a casarse con una mujer que no conoce. ¿Podrá oponerse a ese designio? Una historia de poder, amor y desengaño en los más impactantes escenarios naturales de Asia y Medio Oriente.", '17:15', '18:30'),
(4, 2, 'Günes', "Una mujer que se hizo cargo sola de sus tres hijas decide rehacer su vida cuando ellas se convierten en adolescentes, por lo que conoce a un millonario empresario que le ofrece matrimonio, sin embargo la propuesta originará un drama familiar.",'18:30', '20:00'),
(4, 2, 'La colombiana',"Ángela llega de Colombia a Chile en busca de un futuro mejor, pero a su nuevo vecino no le gustan los inmigrantes. Sin embargo, ella le ayuda a recuperar a su exesposa, quien va a casarse con un exitoso empresario, a cambio de que él cuide a su hijo.", '20:00', '21:00'),
(4, 2, 'Match',"Personas que han dejado de lado su vida sentimental son ayudados por sus hijos para encontrar pareja, quienes tratan de incentivar a sus progenitores a que tengan una vida social y amorosa con la cooperación del presentador que promueve los nexos.", '22:30', '00:15'),
(4, 2, 'Hulya',"Bayram compromete a su hijo Kerim con Melek. Los niños son criados en diferentes ciudades hasta que pueden casarse pero el compromiso se acabará por culpa de Hulya. Ella, logrará casarse con él, aunque no todo saldrá como lo planeó.", '22:45', '23:30'),
(4, 2, 'Josue y la tierra prometida',"La historia del pueblo hebreo tras la muerte de Moisés cuando, liderados por Josué, tendrán que enfrentarse a distintos reinos para tomar Canaán, la tierra que Dios les prometió.", '23:30', '00:30'),
(4, 2, 'El informante',"El nuevo programa de debate y actualidad está a cargo de Juan Manuel Astorga. Un espacio para discutir a fondo temas de actualidad y realidad nacional.", '23:30', '00:30');

-- -------------------------------------
-- Inserción de Keywords de Programas --
-- PROGRAMAS CANAL 13 --
-- ID [1-7] --
INSERT INTO
`qvt-db`.`Keyword` ( `keyword` )
VALUES
('Bienvenidos13'),
('HoróscopoBienvenidos'),
('BV'),
('BV13'),
('TiempoBienvenidos'),
('PregúntaleAMichelle'),
('LaCabinadeLosDeseos'),
-- ID [8-21] --
('Paramparça'),
('Paramparca'),
('Gülseren'),
('Gulseren'),
('Cihan'),
('Dilara'),
('Cansu'),
('Hazal'),
('Ozan'),
('Alpar'),
('Solmaz'),
('Özkan'),
('Ozkan'),
('Keriman'),
-- ID [22-33] --
('EntreDosAmores'),
('Neriman'),
('Macit'),
('Sinasi'),
('Pelin'),
('Faiz'),
('Inci'),
('Gulter'),
('Kerim'),
('Selim'),
('Feyza'),
('Duygu'),
-- ID [34-42] --
('AmorASegundaVista'),
('Zeynep'),
('Fatih'),
('Sevket'),
('Gulsum'),
('Fehmi'),
('Mukkades'),
('Irem'),
('Selin'),
-- ID [43-44] --
('LaPequeñaCasaEnLaPradera'),
('FamiliaIngalls'),
-- ID [45-52] --
('Vertigo'),
('Vértigo2017'),
('Vertigo2017'),
('VertigoCanal13'),
('BotónDePánico'),
('Yerko'),
('YerkoPuchento'),
('JuevesDeVerdad'),
-- ID [53-65] --
('Kosem'),
('ComunidadOtomana'),
('Murad'),
('Ahmed'),
('Iskender'),
('Safiye'),
('Handan'),
('Halime'),
('Fahriye'),
('ShahinGiray'),
('MehmedGiray'),
('Zulfikar'),
('Dervish'),
-- ID [66-70] --
('MasterChefChile'),
('MasterChefChile3'),
('MasterChef3'),
('MC'),
('CajaMisteriosa'),
-- FIN PROGRAMAS CANAL 13 --

-- -----------------------
-- PROGRAMAS CHILEVSION --
-- ID [71-72] --
('LaMañana'),
('matinaldechv'),
-- ID [73-74] --
('LaJueza'),
('lajuezachv'),
-- ID [75-76] --
('LoQueCallamosLasMujeres'),
('Lo_Que_Callamos'),
-- ID 77 --
('VidasEnRiesgo'),
-- ID 78 --
('CasoCerrado'),
-- ID [79-82] --
('EspíasDelAmor'),
('EspiasDelAmor'),
('CatfishChile'),
('espiasdelamor'),
-- ID [83-84] --
('PrimerPlano'),
('_PrimerPlano'),
-- FIN PROGRAMAS CHILEVISION --

-- -----------------
-- PROGRAMAS MEGA --
-- ID [85-93] --
('MuchoGustoMEGA'),
('MuchoGusto'),
('DesayunoGustoMG'),
('PatrullaJuvenilMG'),
('CocinaGustoMG'),
('LaPolémicaDelDíaMG'),
('LaPolemicaDelDiaMG'),
('ConfesionesMatinalesMG'),
('LaConversaMG'),
-- ID [94-95] --
('Amanda'),
('amandateleserie'),
-- ID 96 --
('Sevda'),
-- ID 97 --
('ElRegresoDeLucas'),
-- ID [98-100] --
('ElSecretoDeFeriha'),
('Feriha'),
('Emir'),
-- ID [101-103] --
('TranquiloPapá'),
('TranquiloPapa'),
('TranquiPapaMEGA'),
-- ID [104-105] --
('PerdonaNuestrosPecados'),
('PnpMega'),
-- ID [106-113] --
('MorandéConCompañia'),
('MorandeConCompañia'),
('MCCoficial'),
('MCC'),
('MCC2017'),
('MCCEstelar'),
('ElMuro'),
('KikeMorandé'),
-- ID [114-117] --
('DobleTentación'),
('DobleTentacion'),
('DobleTentacionM'),
('NocheDeMemes'),
-- ID [118-122] --
('MásValeTarde'),
('MasValeLate'),
('MVT'),
('LaVozDeLaCalle'),
('LaCarta'),
-- FIN PROGRAMAS MEGA --

-- ----------------
-- PROGRAMAS TVN --
-- ID [123-127] --
('MuyBuenosDías'),
('MuyBuenosDias'),
('MuyBuenosDíasTVN'),
('BuenosDiasTVN'),
('buenosdiastvn2'),
-- ID [128-140] --
('Elif'),
('ElifTVN'),
('Melek'),
('Kenan'),
('Arzu'),
('Aliye'),
('Tügce'),
('Tugce'),
('Selim'),
('Zeynep'),
('Murat'),
('Veysel'),
('Gonca'),
-- ID [141-144] --
('SarasYKumud'),
('sarasykumudTV'),
('Saras'),
('Kumud'),
-- ID [145-151] --
('Günes'),
('GunesTVN'),
('Gunes'),
('Haluk'),
('Nazli'),
('Ali'),
('Savas'),
-- ID [152-153] --
('LaColombiana'),
('LaColombianaTVN'),
-- ID [154-155] --
('Match'),
('MatchTVN'),
-- ID [156-160] --
('Hulya'),
('HulyaTVN'),
('Bayram'),
('Kerim'),
('Huseyin'),
-- ID [161-166] --
('JosueYLaTierraPrometida'),
('JosueTVN'),
('Josue'),
('Aruna'),
('Marek'),
('Kalesi'),
-- ID [167-168] --
('ElInformante'),
('ElInformanteTVN');
-- FIN PROGRAMAS TVN --

-- -------------------------------------------
-- Asociación de Programas con su Categoria --
INSERT INTO
`qvt-db`.`Programa_Categoria` ( `programa_id`,`categoria_id` )
VALUES
(1,1),
(2,2),
(3,2),
(4,2),
(5,2),
(6,3),
(7,2),
(8,6),
(9,1),
(10,5),
(11,2),
(12,2),
(13,5),
(14,4),
(15,3),
(16,1),
(17,7),
(18,2),
(19,2),
(20,2),
(21,7),
(22,7),
(23,3),
(24,4),
(25,2),
(26,1),
(27,2),
(28,2),
(29,2),
(30,7),
(31,3),
(32,2),
(33,2),
(34,2);

-- -----------------------------------------
-- Asociación de Programas con su Keyword --
-- CANAL 13 --
INSERT INTO
`qvt-db`.`Programa_Keyword` ( `programa_id`,`keyword_id` )
VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(2,8),
(2,9),
(2,10),
(2,11),
(2,12),
(2,13),
(2,14),
(2,15),
(2,16),
(2,17),
(2,18),
(2,19),
(2,20),
(2,21),
(3,22),
(3,23),
(3,24),
(3,25),
(3,26),
(3,27),
(3,28),
(3,29),
(3,30),
(3,31),
(3,32),
(3,33),
(4,34),
(4,35),
(4,36),
(4,37),
(4,38),
(4,39),
(4,40),
(4,41),
(4,42),
(5,43),
(5,44),
(6,45),
(6,46),
(6,47),
(6,48),
(6,49),
(6,50),
(6,51),
(6,52),
(7,53),
(7,54),
(7,55),
(7,56),
(7,57),
(7,58),
(7,59),
(7,60),
(7,61),
(7,62),
(7,63),
(7,64),
(7,65),
(8,66),
(8,67),
(8,68),
(8,69),
(8,70),
-- CHILEVISION --
(9,71),
(9,72),
(10,73),
(10,74),
(11,75),
(11,76),
(12,77),
(13,78),
(14,79),
(14,80),
(14,81),
(14,82),
(15,83),
(15,84),
-- MEGA --
(16,85),
(16,86),
(16,87),
(16,88),
(16,89),
(16,90),
(16,91),
(16,92),
(16,93),
(17,94),
(17,95),
(18,96),
(19,97),
(20,98),
(20,99),
(20,100),
(21,101),
(21,102),
(21,103),
(22,104),
(22,105),
(23,106),
(23,107),
(23,108),
(23,109),
(23,110),
(23,111),
(23,112),
(23,113),
(24,114),
(24,115),
(24,116),
(24,117),
(25,118),
(25,119),
(25,120),
(25,121),
(25,122),
-- TVN --
(26,123),
(26,124),
(26,125),
(26,126),
(26,127),
(27,128),
(27,129),
(27,130),
(27,131),
(27,132),
(27,133),
(27,134),
(27,135),
(27,136),
(27,137),
(27,138),
(27,139),
(27,140),
(28,141),
(28,142),
(28,143),
(28,144),
(29,145),
(29,146),
(29,147),
(29,148),
(29,149),
(29,150),
(29,151),
(30,152),
(30,153),
(31,154),
(31,155),
(32,156),
(32,157),
(32,158),
(32,159),
(32,160),
(33,161),
(33,162),
(33,163),
(33,164),
(33,165),
(33,166),
(34,167),
(34,168);
--