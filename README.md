PopularMovie
============

Android app that allows you to list the most popular movies.
You can also add movies to a list of favorites.

[![Build Status](https://travis-ci.org/olivejp/PopularMovie.svg?branch=master)](https://travis-ci.org/olivejp/PopularMovie)
[![Build Status](https://www.bitrise.io/app/3f16bf162aa3542f/status.svg?token=wLcTbLnRjnnQSjT8qVelBQ)](https://www.bitrise.io/app/3f16bf162aa3542f)

Install
-------

To work, this application needs a themoviedb account.
If you don't have one, go to : [themoviedb.org](https://www.themoviedb.org/)
Once you've created your account, go to [Your settings](https://www.themoviedb.org/settings/api) and you'll find your API Key (v3 auth).

Depending on which type of build you want, follow those steps :

    ### Debug build

    In the build.gradle file, replace "your_api_key_here" by your API key :
    ```java
    debug {
        buildConfigField("String", "API_KEY", "your_api_key_here")
    }
    ```

    ### Release build

    For the release build, create a new environment variable named **ORG_GRADLE_PROJECT_API_KEY**
    Put your API key into it.

    Gradle will automatically found your variable and you'll be able to call it into your script through API_KEY variable.

Contact
-------

Feel free to contact [me](orlanth23@gmail.com)

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
