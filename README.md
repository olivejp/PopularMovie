PopularMovie
============

Android app, that allow you to list the most popular movies.
You can also add movies to a list of favorites.

[![Build Status](https://travis-ci.org/olivejp/PopularMovie.svg?branch=master)](https://travis-ci.org/olivejp/PopularMovie)

Install
-------

To work, this application need an themoviedb account.
If you don't have one, go to : [themoviedb.org](https://www.themoviedb.org/)
Once you're logged, go to [Your settings](https://www.themoviedb.org/settings/api) and you'll find your API Read Access Token.

Add your movieDB API key on the utils/Constants class in MOVIE_DB_API_KEY attribute.
```java
public static final String MOVIE_DB_API_KEY = "my_api_read_access_token";
```

License
-------
    Copyright 2017 OLIVE Jean-Paul

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
