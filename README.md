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

Winning chance of the lot is the one defied at CentralDogma, i.e., if you use 'chance.json' above as it is, the
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

Draw a lot to access [http://127.0.0.1:8080/draw](http://127.0.0.1:8080/draw), and you'll see in log that the
winning chance changed.

```shell
c.c.demo.service.LotteryService          : The chance of winning is 90%
```

## how to use CentralDogma

Assume you are using Spring Framework.

### add dependency

Add `com.linecorp.centraldogma:centraldogma-client-spring-boot-starter:0.52.1` to build.gradle.kts.

```kotlin
dependencies {
    implementation("com.linecorp.centraldogma:centraldogma-client-spring-boot-starter:0.52.1")
}
```

### add configuration

Add a new section called `centraldogma` which contains some settings, e.g., hosts.

```yaml
centraldogma:
  hosts:
    - 127.0.0.1:36462
```

### create watcher

When you finish adding dependency and configuration, you can use `com.linecorp.centraldogma.client.CentralDogma`
. So, inject the `client.CentralDogma` into your application.

Create `com.linecorp.centraldogma.client.Watcher` to use a method of `client.CentralDogma` which is injected.

One of the method to create the `client.Wathcer` is `fileWatcher`.

| name | type | description | 
| --- | --- | --- | 
| projectName | String | project name of CentralDogma | 
| repositoryName | String | repository name of CentralDogma | 
| query | com.linecorp.centraldogma.common.Query\<T\> | query on the file | 
| function | java.util.function.Function\<? super T, ? extends U\> | function which converts from (? super T) to (? extends U) | 

If you put json file in CentralDogma, you use `Query.ofJson(jsonPath)` as a third argument, so a fourth argument
is `Function<? Super JsonNode, ? extends T>`.

#### awaitInitialValue

You can specify a timeout and a default value by using `watcher.awaitInitialVallue()`. If not using this method,
your application has possibility of awaiting indefinitely.

According to
an [official document](https://line.github.io/centraldogma/client-java.html?highlight=awaitinitial#preparing-for-unavailability)
, recommended timeout value is 20 seconds or more.
> It is generally recommended to use a value not less than 20 seconds so that the client can retry at least a few times before timing out.

### get latest value

Get the latest value by using `watcher.latestValue()`.

## reference

https://line.github.io/centraldogma/
