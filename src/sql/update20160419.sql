alter table cutplanlumberlogdiagram add SmallDiameter decimal(10,2) NOT NULL,
                                 add MediumDiameter decimal(10,2) NOT NULL,
                                 add BigDiameter decimal(10,2) NOT NULL,
                                 add Length decimal(10,2) NOT NULL,
                                 add Volume decimal(20,2) NOT NULL,
                                 add Reallength decimal(10,2) NOT NULL,
                                 add Realvolume decimal(20,2) NOT NULL;

                                 alter table cutplanproduct add ProductVolume decimal(20,2);