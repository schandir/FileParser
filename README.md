*************************
File Parser
*************************

The file parser will take in a file as input parameter and will parse the file based on the delimitter provided. the parsed file will be transformed to an output model as defined in the requirement. Optionally a fileFormat parameter can be passed that can define the datatype format of the columns in the file. If file format not provided, it defaults to the number of columns as 4 and data type formats as below.

Column Datatype
1		date
2		number
3		string
4		double

The application distribution comes as a zip file, when extracted has a lib folder and a config folder. The config folder has a 'application.configuration' file which reads the application properties. We can configure the application port, web context etc. 

Steps to run the application:
1. Unzip the FileParser-0.0.2.0.zip
2. Open a command prompt and run "java -jar libs/FileParser*.jar > FilePatrser.log"
3. Using RESTClient like google rest client or postman below endpoint can be invoked. 

By default the applications binds to the port 8090 and starts with the web context '/fileParser'. Below are the url (API and UI) and sample payloads for API.

The Application has a minimal UI as it is built with minimal UI knowledge.

UI:

Url: http://localhost:8090/fileParser/

The form will have two text field one for file location and one for delimitter.

On submit a response with each line entry from model will be displayed on the browser. It is not pretty. Sorry for inconvenience. 

Sample response: 
Result:
FileParserOutputModel.OutputLine(rowDate=31/3/07 3:32:12 AM EDT, description=Walget's Widgets, value=$50.00)
FileParserOutputModel.OutputLine(rowDate=null, description=Walget's Widgets, value=$0.00)
FileParserOutputModel.OutputLine(rowDate=2/4/06 2:23:47 PM EDT, description=BMart Bubbles, value=$5.50)
FileParserOutputModel.OutputLine(rowDate=18/7/06 1:53:23 PM EDT, description=Kgreen Kandies, value=$0.23)
FileParserOutputModel.OutputLine(rowDate=24/12/06 10:0:08 PM EST, description=Santa Snacks, value=$2,000,065.61)


API:

Url: http://localhost:8090/fileParser/v1/file-parser

Sample Request Payload:
{
  "fileLocation": "C:\\Personal\\boomi\\sample.txt",
  "delimiter": ","
}

Sample Response Payload:
{
        "resultCode": "OK",
        "resultSubCode": "",
        "serviceTransactionId": "453c41b4a0a1409aad30d6572ed2d9e0",
        "clientRequestId": "453c41b4a0a1409aad30d6572ed2d9e0",
        "data": {
        "outputLines": [
          {
            "rowDate": "31/3/07 3:32:12 AM EDT",
            "description": "Walget's Widgets",
            "value": "$50.00"
            },
          {
            "rowDate": null,
            "description": "Walget's Widgets",
            "value": "$0.00"
            },
          {
            "rowDate": "2/4/06 2:23:47 PM EDT",
            "description": "BMart Bubbles",
            "value": "$5.50"
            },
          {
            "rowDate": "18/7/06 1:53:23 PM EDT",
            "description": "Kgreen Kandies",
            "value": "$0.23"
            },
          {
            "rowDate": "24/12/06 10:0:08 PM EST",
            "description": "Santa Snacks",
            "value": "$2,000,065.61"
            }
        ],
            "rejectedLines": [],
        }
    }

Json above represents the parsed lines and rejectedLines represents lines that are not adhering the format.
