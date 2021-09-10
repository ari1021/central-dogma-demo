# central-dogma-demo

## startup

There are CentralDogma Docker images in [Docker Hub](https://hub.docker.com/r/line/centraldogma/), so pull and
run.

```shell
docker run -p 36462:36462 line/centraldogma:0.52.1
```

Open [http://127.0.0.1:36462](http://127.0.0.1:36462) in your browser to access administrative console.

## settings for this repository

MEMO: We can use [command-line client](https://line.github.io/centraldogma/client-cli.html) for some settings,
but it's easy to use administrative console.

1. Create project 'demo'.
1. Create repository 'lottery' in 'demo' project.
1. Create json file 'chance.json' in 'lottery' repository as below.

```json
{
  "chance": 50
}
```

See [chance_sample.json](https://github.com/ari1021/central-dogma-demo/blob/main/src/main/kotlin/com/centraldogma/demo/util/chance_sample.json)
.

## run application

Run Application to use following command at project root.

```shell
./gradlew bootRun
```

You can draw a lot to access [http://127.0.0.1:8080/draw](http://127.0.0.1:8080/draw).

Winning chance of the lot is the one defied at CentralDogma. i.e. If you use 'chance.json' above as it is, the
winning chance is 50%.

The winning chance appears in log like this.

```shell
c.c.demo.service.LotteryService          : The chance of winning is 50%
```

## change settings

CentralDogma enables us to use new settings **without restarting application**.

Change chance value in 'chance.json'.

```json
{
  "chance": 90
}
```

Draw a lot to access [http://127.0.0.1:8080/draw](http://127.0.0.1:8080/draw), and you'll see that the winning
chance changed in log.

```shell
c.c.demo.service.LotteryService          : The chance of winning is 90%
```