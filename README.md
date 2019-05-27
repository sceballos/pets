# Pets Android App

Demo app with cloud data fetch and date analysis feature for Pets Store Business.

### Overview

This app uses two files :

•config.json : contains store opening days and times and if there is chat or call features enabled

•pets.json : contains data of pets in the store

Stored currently in this two urls :

• - [config.json](https://drive.google.com/uc?authuser=0&id=1PefOGt_R18jPYkCqghVh9xyGVraz0_PE&export=download)
• - [pets.json](https://drive.google.com/uc?authuser=0&id=1ON6u7zD-inQBkelKiTmfqwr1VFTs8ZeK&export=download)

• The app will attempt to download this files when its open. If no internet is available it will use already store data
to load the UI.

• If there is no previous data saved and no internet the app will not be usable.

## Project structure

You can find the next package structure in the project

>{app-package-name}   
		•adapter  
		•data
		•network  
		•ui
		•util

•**adapter** : Holds necessary adapters for recycler views across the app
•**data** : Holds data structures used across the app
•**network** : Holds network related classes
•**ui** : Holds activities used across the app
•**util** : Holds various utility classes


## Usage

You can test UI changes based on config.json and pets.json by changing their values.
This can be accomplished in two ways:

>Local way

Inside MainActivity.class you will find two functions that read the values of the json files :

• readConfigJson() [Line 191]
• readPetJson()    [Line 201]

Change this lines 

```
String configDataRaw = FilesUtil.readJsonData(MainActivity.this, Constants.CONFIG_FILE_NAME);
String petsDataRaw = FilesUtil.readJsonData(MainActivity.this, Constants.DATA_FILE_NAME);
```

to : 

```
String configDataRaw = FilesUtil.readFileFromRawDirectory(MainActivity.this, R.raw.config);
String petsDataRaw = FilesUtil.readFileFromRawDirectory(MainActivity.this, R.raw.data);
```

This will force the app to use local data stored in [res > raw] folder.
You can modify values in those values and see the changes on the UI.


>Cloud way

Change download urls of config and data files to your preference data urls. This can be done inside [util.Constants] class.
Modify this two constants :

```
public static final String CONFIG_FILE_URL = "https://drive.google.com/uc?authuser=0&id=1PefOGt" +
        "_R18jPYkCqghVh9xyGVraz0_PE&export=download";

public static final String DATA_FILE_URL = "https://drive.google.com/uc?authuser=0&id=1ON6u7zD-" +
        "inQBkelKiTmfqwr1VFTs8ZeK&export=download";
```

## About Working Hours data

Working hours takes a formatted string as follows :

```
"D-D 00:00 - 00:00"
```

• D : can be any value of the following :

```
"Su" > Sunday
"M" > Monday
"Tu" > Tu
"W" > W
"Th" > Th
"F" > F
"Sa" > Sa

```

• Hours : any from 00:00 to 23:59

Note : Using a different format will cause to make the working hours unreadable. This will be reflected when the user attempts to call/chat the Pet Store.



