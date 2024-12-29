# Local Kafka Workflow

First we want to run our Kafka broker locally. We could do so with following command

```bash
docker run -p 9092:9092 apache/kafka:3.7.0
```

However, we can name our broker and run it in the background using command

```bash
docker run -d --name kafka-broker -p 9092:9092 apache/kafka:3.7.0
```

Then we must create a Kafka topic, ensuring we add appropriate topic level settings.

We can run this locally using [kafka-topics.sh](https://kafka.apache.org/documentation/#topicconfigs)

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

Now we can switch to our program.
We can run the tests of our scala program using

```bash
sbt test
```

Now run our scala producer program

```bash
sbt run
```

You can search for logs `successfully pushed records onto kafka topic people-topic`.

To perform a sanity check when in debug mode, lets consume 10 messages and print both the key and value
- `f1474ca118fa` in the givne example refers to the container ID.
- You can retrieve this using `docker ps`.
- You must replace with your container id.
-
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

It is then important we shut down our docker container safely. When you are finished using the kafka environment, use the following commands

```bash
docker stop kafka-broker
```

```bash
docker rm kafka-broker
```