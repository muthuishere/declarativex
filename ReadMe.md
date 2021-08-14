###DeclarativeX
###### Functional Programming made declarative in a better way

A Fluent Style Library to ease declarative programming.


 
* [Try](#Try)
* [Filter](#Filter)

#### Install

Maven

```
        <dependency>
            <groupId>io.github.muthuishere</groupId>
            <artifactId>declarativex</artifactId>            
        </dependency>
```
    
Gradle
```
implementation "io.github.muthuishere:declarativex"
```

Usage
```
import declarativex.Try;
import declarativex.Filter;



```


## Try
<b>Instead of Writing This</b> 

```
List<String> results = null;

        try {
            results =newsService.downloadFromNyTimes(topic);
        }catch (Exception e){

            try {

                results =newsService.downloadFromHerald(topic);
            }catch (Exception e1){
                try {
                    results =newsService.downloadFromSun(topic);
                }catch (Exception e2){


                }
            }
        }

```
 
<b>Write This</b>
 
 ```
import declarativex.Try;

        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                        .or(()->newsService.downloadFromHerald(topic))
                        .or(()->newsService.downloadFromSun(topic))
                        .get();

```

<b>Or Even Better way</b>
```
results= Try.any(newsService::downloadFromNyTimes,
                         newsService::downloadFromHerald,
                         newsService::downloadFromSun)
                        .with(topic)
                        .orElseGet(Arrays.asList("Some default"));

```

There are Additional Logging , Peek ,PeekError ,default value can convert to Optional as well

Also there is a lazy version of the same

```
results = Try.lazy.from(this::downloadCacheData)
                .get();

//Evaluation happens only after get is Invoked

``` 


##Filter
<b>Instead of Writing This</b>

```

        if (age > 60) {
            result = "Can Retire";
        } else if (age > 40) {
            result = "Can Go World Tour";
        } else if (age > 32) {
            result = "Can Go for Job";
        } else if (age > 18) {
            result = "Can Go To College";
        } else {
            result = "Can Play";
        }

``` 

<b>Write This</b>

```
 result = Filter.If(() -> age > 60)
                .then("Can Retire")
                .elseIf(() -> age > 40)
                .then("Can Go World Tour")
                .elseIf(() -> age > 32)
                .then("Can Go for Job")
                .elseIf(() -> age > 18)
                .then("Can Go To College")
                .elseThen("Can Play")
                .get();

```

