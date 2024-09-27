### DeclarativeX
######  A Composable approach to exception & conditions in Java

![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)



![Composing Exceptions with DeclarativeX](https://github.com/muthuishere/declarativex-example/blob/main/assets/withthis1.png?raw=true)

----


![Composing Exceptions with DeclarativeX for same parameters](https://github.com/muthuishere/declarativex-example/blob/main/assets/withthis2.png?raw=true)

----

![On Error](https://github.com/muthuishere/declarativex-example/blob/main/assets/onerror3.png?raw=true)


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

