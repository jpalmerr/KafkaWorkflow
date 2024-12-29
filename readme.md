# Local Kafka Workflow

### Cloning the repo

Via ssh, run 

```
git clone git@github.com:jpalmerr/KafkaWorkflow.git
```

### Environment set up

Note these instructions assume both Kafka and Scala are installed locally. 
- kafka version `3.7.0`
- scala version `2.13.15`

To run this app in accordance to the requirements, we must have a kafka broker running locally.

To do so you could run the following command.

```bash
docker run -p 9092:9092 apache/kafka:3.7.0
```

However, we can name our broker and run it in the background using command

```bash
docker run -d --name kafka-broker -p 9092:9092 apache/kafka:3.7.0
```

The requirements demand a kafka topic is created on the local broker. We must add the appropriate topic level settings,
in particular 
- partitions: `3`
- clean up policy `delete`

We can do this this locally using [kafka-topics.sh](https://kafka.apache.org/documentation/#topicconfigs).

```bash
docker exec -it kafka-broker /opt/kafka/bin/kafka-topics.sh \
  --create \
  --topic people-topic \
  --partitions 3 \
  --replication-factor 1 \
  --bootstrap-server localhost:9092 \
  --config cleanup.policy=delete
```
If this has run successfully you should see log `Created topic people-topic`.

### Running the app

We can run local unit tests using command

```bash
sbt test
```

The first step of this flow is to run the Producer App. This app 
- loads our data source `random-people-data.json`
- push this data onto local kafka broker's topic `people-topic`

Implementation notes:
- there is a typo in the data source at `postode`.
- The app handles this accordingly, and fixes it when pushed onto the topic, as opposed to interfering with the data source.

To run the Producer App, use command: 

```bash
sbt run
```
When prompted, elect to run the `KafkaProducerApp`

You can search for logs `successfully pushed records onto kafka topic people-topic`.

To perform a sanity check when in debug mode, lets consume 10 messages and print both the key and value
- `f1474ca118fa` in the given example refers to the container ID.
- You can retrieve this using `docker ps`.
- You must replace with your container id.

```bash
docker exec -it f1474ca118fa /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic people-topic --from-beginning --max-messages 10 --property print.key=true
```

This command consumes messages from the people-topic Kafka topic starting from the beginning. It fetches up to 10 messages and prints both the key and value of each message

An example response may look like

```
S94NE1SQQDEY14G6	{"_id":"S94NE1SQQDEY14G6","name":"Nana Tilley","dob":"2017-05-23","address":{"street":"1065 Capesthorne Road","town":"Ferryhill","postcode":"IP05 4YQ"},"telephone":"+507-5229-696-588","pets":["Nala","Bailey"],"score":3.3,"email":"alphonso-ochs4@yahoo.com","url":"https://buried.com","description":"my colleagues beats slow breeding espn discrimination link para calculated interaction dutch agrees relatively hamburg advertise broke editor characteristic adopted","verified":true,"salary":14985}
MAMM9FJOS8X33FY1	{"_id":"MAMM9FJOS8X33FY1","name":"Julia Montanez","dob":"2017-04-01","address":{"street":"0212 Drury Street","town":"Surbiton","postcode":"CF56 0NU"},"telephone":"+591-8438-103-929","pets":["Baby","Buddy"],"score":4.5,"email":"nan-ashcraft@hotmail.com","url":"http://www.delays.com","description":"beast efforts manual fleece economics compliant clusters nose letter documented attacked cite cursor refrigerator dangerous started cornwall wallpaper noise newer","verified":false,"salary":37424}
VBMOXCULQ2KVPKLT	{"_id":"VBMOXCULQ2KVPKLT","name":"Opal Mosley-Boyle","dob":"2016-09-02","address":{"street":"9745 Catlow Road","town":"Croydon","postcode":"BH62 0FN"},"telephone":"+353-6965-986-655","pets":["Gizmo","Lilly"],"score":8.9,"email":"fannie-wickham3790@gmail.com","url":"https://exhibits.com","description":"grip woods alert thumb worn ill convenient construction choir registrar darkness bradley decided occasionally resolved jd isaac generation losing extreme","verified":true,"salary":52602}
PVOODJ9XIPOSFDPS	{"_id":"PVOODJ9XIPOSFDPS","name":"Rachal Ochs","dob":"2015-05-17","address":{"street":"2881 Carrsvale Street","town":"Gornal","postcode":"ZE26 8HX"},"telephone":"+670-7675-694-753","pets":["MIMI","Henry"],"score":9.2,"email":"deena87153@mine.com","url":"https://anytime.yoshikawa.saitama.jp","description":"vatican blackjack diagnostic political davidson marsh birmingham equipped sony that oz fg sphere hydrogen publications linking atm ciao socket advised","verified":false,"salary":36738}
1UEZ6CD39RYPLDE3	{"_id":"1UEZ6CD39RYPLDE3","name":"Ethelene Deluca","dob":"2022-04-02","address":{"street":"1709 Middleham Road","town":"Helmsley","postcode":"FY5 0TF"},"telephone":"+509-7541-679-632","pets":["Zoe","Cody"],"score":6.1,"email":"france7689@gmail.com","url":"https://www.interpretation.com","description":"hotel popularity s urw islam jj tariff tune warming user fool republican qualified minutes road planners appreciated testament wizard food","verified":true,"salary":48897}
K4S4R2TN4CDB61BI	{"_id":"K4S4R2TN4CDB61BI","name":"Hiram Huff","dob":"2020-12-04","address":{"street":"3960 Glencoyne Avenue","town":"Brightlingsea","postcode":"RM0 2CW"},"telephone":"+353-4854-822-601","pets":["Midnight","Ginger"],"score":5.5,"email":"vellaluna2@hotmail.com","url":"https://budapest.com","description":"flooring last attitudes nuke jefferson vernon work alternative lenses z scott governance seems bass characterization treasure tobacco assigned nick deeper","verified":true,"salary":62809}
ZT8C50RBQLTTCZKC	{"_id":"ZT8C50RBQLTTCZKC","name":"Kristen Samson","dob":"2022-11-21","address":{"street":"9471 Martland Avenue","town":"Blackpool","postcode":"FK02 3NY"},"telephone":"+212-8084-050-337","pets":["Lilly","Lilly"],"score":4.0,"email":"evette.driscoll1953@yahoo.com","url":"http://calibration.yukuhashi.fukuoka.jp","description":"valued mobility dallas polar endless editions sky swimming setting closed draft such join reseller space mine lc enquiries expand observed","verified":true,"salary":34932}
4OOTAD37G5DI796V	{"_id":"4OOTAD37G5DI796V","name":"Myrtle Noriega","dob":"2015-11-12","address":{"street":"7645 Highcliffe Road","town":"Eaglescliffe","postcode":"TD4 6SM"},"telephone":"+352-2787-038-498","pets":["Loki","Lucky"],"score":1.9,"email":"evette.burnett@gmail.com","url":"https://www.met.com","description":"custody sandy event souls qualifying reading sat estate round refuse commodity ivory china fatty publications prairie auditor dosage ticket restriction","verified":false,"salary":11215}
8SVOT1XGU20IDFN0	{"_id":"8SVOT1XGU20IDFN0","name":"Veronique Mccleary","dob":"2023-07-02","address":{"street":"2257 Coulsden Street","town":"Middleton","postcode":"LN6 3OC"},"telephone":"+689-3816-606-572","pets":["Princess","Apollo"],"score":6.4,"email":"jamicoronado@hotmail.com","url":"http://chrome.com","description":"outlet ripe consequently advised ton schedules gene rogers sk merge prediction prep methodology draft exhibit aol looked sunny produced wives","verified":true,"salary":42909}
51AR0Y984N93CQYT	{"_id":"51AR0Y984N93CQYT","name":"Vern Yoo","dob":"2020-08-17","address":{"street":"5883 Waterford Lane","town":"Porthcawl","postcode":"NW0 2KG"},"telephone":"+58-6901-373-061","pets":["Toby","Leo"],"score":6.6,"email":"sterling5@bon.com","url":"https://cork.info.zm","description":"setting optimum troubleshooting drill thousand pci ts jeep stones wr throat compact tracy server besides feelings comparisons receivers groundwater gc","verified":true,"salary":25819}
Processed a total of 10 messages
```

A response similar to above, will confirm that we have successfully pushed data onto our `people-topic`, and can proceed with our consumer interface. 

To run the Consumer App, use command:  

```bash
sbt run
```

When prompted, elect to run the `KafkaConsumerApp`

This will spin up a running server. We can then test the api by opening our browser and heading to

```
http://localhost:8080/topic/people-topic?offset={{int}}&count={{count}}
```

- We should receive an appropriate json response.
- We should see that we receive count (or up to count if partition doesn't reach count) responses, for each partition (0, 1, 2)

Implementation notes:
- Both query parameters are optional
- If offset is not provided, it defaults to 0. This was chosen as offsets are zero based. 
- If count is not provided, the code will default to 10

### Example response 

`http://localhost:8080/topic/people-topic?offset=2&count=5`

```
[
  {
    "partition": "0",
    "offset": "2",
    "value": "{\"_id\":\"IQSOB8A518JJDJSU\",\"name\":\"Hulda Shannon\",\"dob\":\"2015-03-17\",\"address\":{\"street\":\"6704 Hopkin Circle\",\"town\":\"Hucknall\",\"postcode\":\"NG4 8FS\"},\"telephone\":\"+92-1352-538-491\",\"pets\":[\"Panda\",\"Apollo\"],\"score\":5.0,\"email\":\"josie0414@chubby.com\",\"url\":\"https://www.wu.com\",\"description\":\"kb blocked moves surveillance congressional dream chess reliability fisheries hat matter voices sheet xanax poison study current bo introducing muslim\",\"verified\":true,\"salary\":10732}"
  },
  {
    "partition": "0",
    "offset": "3",
    "value": "{\"_id\":\"380LQZ8FZ4Y1E5UM\",\"name\":\"Pablo Kelley\",\"dob\":\"2016-09-23\",\"address\":{\"street\":\"1484 Back Street\",\"town\":\"Grangemouth\",\"postcode\":\"NN41 3HE\"},\"telephone\":\"+213-9441-150-946\",\"pets\":[\"Smokey\",\"Dexter\"],\"score\":4.3,\"email\":\"leonarda52960@intend.com\",\"url\":\"http://www.watt.com\",\"description\":\"arctic staffing chris manufacturers mirrors established hat blocking creek camcorders nasa happens waves bang purposes animation tag buses worried vernon\",\"verified\":true,\"salary\":49015}"
  },
  {
    "partition": "0",
    "offset": "4",
    "value": "{\"_id\":\"7LHHLMQYR5NCBIUL\",\"name\":\"Concha Creighton\",\"dob\":\"2017-07-11\",\"address\":{\"street\":\"7360 Saville Street\",\"town\":\"Abergavenny\",\"postcode\":\"DL63 5ZL\"},\"telephone\":\"+974-0959-773-983\",\"pets\":[\"Smokey\",\"Dexter\"],\"score\":2.7,\"email\":\"elease.steel687@criteria.com\",\"url\":\"http://ranger.com\",\"description\":\"caring reporters hill sustained alloy tattoo limit transparency profits critical restructuring extremely triple arctic beans raised antibody territory fig moms\",\"verified\":true,\"salary\":58431}"
  },
  {
    "partition": "0",
    "offset": "5",
    "value": "{\"_id\":\"NCOEPX8BG6HMLZS3\",\"name\":\"Angie Novak\",\"dob\":\"2021-01-30\",\"address\":{\"street\":\"5365 Clegg Street\",\"town\":\"Goodwick\",\"postcode\":\"WC80 4XP\"},\"telephone\":\"+595-5446-323-807\",\"pets\":[\"Ziggy\",\"Riley\"],\"score\":3.3,\"email\":\"almeda_corbin441@gmail.com\",\"url\":\"http://www.anger.com\",\"description\":\"programmer blend question iceland naples custom logos optimize outlets rebates cream entitled everywhere upgrading preferred sv lp unlike aids gift\",\"verified\":true,\"salary\":27848}"
  },
  {
    "partition": "0",
    "offset": "6",
    "value": "{\"_id\":\"ECK8PCD10NDDCF7G\",\"name\":\"Betsey Duval\",\"dob\":\"2016-08-25\",\"address\":{\"street\":\"1232 Lindisfarne Avenue\",\"town\":\"Petersfield\",\"postcode\":\"BS26 0HY\"},\"telephone\":\"+264-8420-279-721\",\"pets\":[\"Ziggy\",\"Riley\"],\"score\":3.6,\"email\":\"debby-higginbotham@gmail.com\",\"url\":\"http://www.specialty.com\",\"description\":\"ben commercial epinions demands cause services log exercise arlington diffs inexpensive designers dakota susan qualification genre y purchased climbing hairy\",\"verified\":true,\"salary\":18565}"
  },
  {
    "partition": "1",
    "offset": "2",
    "value": "{\"_id\":\"RT875DSC0LYBQSAN\",\"name\":\"Carmelia Call\",\"dob\":\"2021-11-16\",\"address\":{\"street\":\"9242 Old Street\",\"town\":\"Coalisland\",\"postcode\":\"LS2 3UQ\"},\"telephone\":\"+234-1492-285-846\",\"pets\":[\"Jack\",\"Jack\"],\"score\":5.2,\"email\":\"hank-lattimore@reached.com\",\"url\":\"https://gossip.com\",\"description\":\"chat emission appeal citysearch drop illinois dynamics jesse webpage discussion manufacturers constitute folks lion funds x gbp ho operation andorra\",\"verified\":true,\"salary\":50249}"
  },
  {
    "partition": "1",
    "offset": "3",
    "value": "{\"_id\":\"M277IJL99B0CT8G1\",\"name\":\"Marry Wray\",\"dob\":\"2016-10-21\",\"address\":{\"street\":\"2390 Galland Avenue\",\"town\":\"Chorley\",\"postcode\":\"LL5 3YO\"},\"telephone\":\"+40-7170-399-712\",\"pets\":[\"Ziggy\",\"Tucker\"],\"score\":7.1,\"email\":\"arnette_libby@hotmail.com\",\"url\":\"http://clusters.com\",\"description\":\"responses sentences this foods authors detail walt outstanding bundle spring writers sailing stationery automobiles administered works steering emily odd robert\",\"verified\":true,\"salary\":33782}"
  },
  {
    "partition": "1",
    "offset": "4",
    "value": "{\"_id\":\"8KPO4NAYBKRYO9F2\",\"name\":\"Edyth Wahl\",\"dob\":\"2019-01-22\",\"address\":{\"street\":\"9739 Bark Avenue\",\"town\":\"Boddam\",\"postcode\":\"IV14 4GZ\"},\"telephone\":\"+63-9967-372-619\",\"pets\":[\"Baby\",\"Charlie\"],\"score\":4.0,\"email\":\"felicitarivers@orlando.com\",\"url\":\"http://medication.com\",\"description\":\"filled appliance brutal fcc paint transactions bed jefferson malpractice guns clark resulting screening stockings luke outstanding europe bones malaysia jewelry\",\"verified\":true,\"salary\":10483}"
  },
  {
    "partition": "1",
    "offset": "5",
    "value": "{\"_id\":\"DN1PK3TVB9AGQO8Q\",\"name\":\"Emeline Menard\",\"dob\":\"2016-11-27\",\"address\":{\"street\":\"9303 Sinderland\",\"town\":\"Sedbergh\",\"postcode\":\"LD60 3XP\"},\"telephone\":\"+62-7440-055-357\",\"pets\":[\"bandit\",\"Nala\"],\"score\":1.6,\"email\":\"tyreeray7141@hotmail.com\",\"url\":\"http://existence.com\",\"description\":\"refer director reforms read ind could con rats missions puerto claim wb mem substantial brook concerts swaziland focal king snapshot\",\"verified\":true,\"salary\":26512}"
  },
  {
    "partition": "1",
    "offset": "6",
    "value": "{\"_id\":\"NP915PKIFSP5Q28A\",\"name\":\"Marin Shea\",\"dob\":\"2021-02-03\",\"address\":{\"street\":\"1916 Mimosa Lane\",\"town\":\"Huddersfield\",\"postcode\":\"SL5 7EX\"},\"telephone\":\"+852-4295-274-572\",\"pets\":[\"Pepper\",\"Jake\"],\"score\":5.7,\"email\":\"hope-bills60@yahoo.com\",\"url\":\"http://www.lies.com\",\"description\":\"manager ver dvds fewer breed shops farm recipes face constitutional organizational entitled origins minnesota aviation commissioners smooth holders phi letter\",\"verified\":false,\"salary\":54663}"
  },
  {
    "partition": "2",
    "offset": "2",
    "value": "{\"_id\":\"VBMOXCULQ2KVPKLT\",\"name\":\"Opal Mosley-Boyle\",\"dob\":\"2016-09-02\",\"address\":{\"street\":\"9745 Catlow Road\",\"town\":\"Croydon\",\"postcode\":\"BH62 0FN\"},\"telephone\":\"+353-6965-986-655\",\"pets\":[\"Gizmo\",\"Lilly\"],\"score\":8.9,\"email\":\"fannie-wickham3790@gmail.com\",\"url\":\"https://exhibits.com\",\"description\":\"grip woods alert thumb worn ill convenient construction choir registrar darkness bradley decided occasionally resolved jd isaac generation losing extreme\",\"verified\":true,\"salary\":52602}"
  },
  {
    "partition": "2",
    "offset": "3",
    "value": "{\"_id\":\"PVOODJ9XIPOSFDPS\",\"name\":\"Rachal Ochs\",\"dob\":\"2015-05-17\",\"address\":{\"street\":\"2881 Carrsvale Street\",\"town\":\"Gornal\",\"postcode\":\"ZE26 8HX\"},\"telephone\":\"+670-7675-694-753\",\"pets\":[\"MIMI\",\"Henry\"],\"score\":9.2,\"email\":\"deena87153@mine.com\",\"url\":\"https://anytime.yoshikawa.saitama.jp\",\"description\":\"vatican blackjack diagnostic political davidson marsh birmingham equipped sony that oz fg sphere hydrogen publications linking atm ciao socket advised\",\"verified\":false,\"salary\":36738}"
  },
  {
    "partition": "2",
    "offset": "4",
    "value": "{\"_id\":\"1UEZ6CD39RYPLDE3\",\"name\":\"Ethelene Deluca\",\"dob\":\"2022-04-02\",\"address\":{\"street\":\"1709 Middleham Road\",\"town\":\"Helmsley\",\"postcode\":\"FY5 0TF\"},\"telephone\":\"+509-7541-679-632\",\"pets\":[\"Zoe\",\"Cody\"],\"score\":6.1,\"email\":\"france7689@gmail.com\",\"url\":\"https://www.interpretation.com\",\"description\":\"hotel popularity s urw islam jj tariff tune warming user fool republican qualified minutes road planners appreciated testament wizard food\",\"verified\":true,\"salary\":48897}"
  },
  {
    "partition": "2",
    "offset": "5",
    "value": "{\"_id\":\"K4S4R2TN4CDB61BI\",\"name\":\"Hiram Huff\",\"dob\":\"2020-12-04\",\"address\":{\"street\":\"3960 Glencoyne Avenue\",\"town\":\"Brightlingsea\",\"postcode\":\"RM0 2CW\"},\"telephone\":\"+353-4854-822-601\",\"pets\":[\"Midnight\",\"Ginger\"],\"score\":5.5,\"email\":\"vellaluna2@hotmail.com\",\"url\":\"https://budapest.com\",\"description\":\"flooring last attitudes nuke jefferson vernon work alternative lenses z scott governance seems bass characterization treasure tobacco assigned nick deeper\",\"verified\":true,\"salary\":62809}"
  },
  {
    "partition": "2",
    "offset": "6",
    "value": "{\"_id\":\"ZT8C50RBQLTTCZKC\",\"name\":\"Kristen Samson\",\"dob\":\"2022-11-21\",\"address\":{\"street\":\"9471 Martland Avenue\",\"town\":\"Blackpool\",\"postcode\":\"FK02 3NY\"},\"telephone\":\"+212-8084-050-337\",\"pets\":[\"Lilly\",\"Lilly\"],\"score\":4.0,\"email\":\"evette.driscoll1953@yahoo.com\",\"url\":\"http://calibration.yukuhashi.fukuoka.jp\",\"description\":\"valued mobility dallas polar endless editions sky swimming setting closed draft such join reseller space mine lc enquiries expand observed\",\"verified\":true,\"salary\":34932}"
  }
]
```

### Shutting down environment

It is then important we shut down our docker container safely. When you are finished using the kafka environment, use the following commands

```bash
docker stop kafka-broker
```

```bash
docker rm kafka-broker
```