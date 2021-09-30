/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var fs = require("fs");
var path = require("path");

function rootBuildGradleExists() {
    var target = path.join("platforms", "android", "build.gradle");
    return fs.existsSync(target);
}

/*
 * Helper function to read the build.gradle that sits at the root of the project
 */
function readRootBuildGradle() {
    var target = path.join("platforms", "android", "build.gradle");
    return fs.readFileSync(target, "utf-8");
}

/*
 * Added a dependency based on the position of the know 'com.android.tools.build' dependency in the build.gradle
 */
function addDependencies(buildGradle) {
    // find the known line to match
    var match = buildGradle.match(/^(\s*)classpath 'com.android.tools.build(.*)/m);
    var whitespace = match[1];
    var huaweiDependency = whitespace + 'classpath \'com.huawei.agconnect:agcp:1.4.2.301\' // huawei service dependency from cordova-plugin-agc-crash'
    var modifiedLine = match[0] + '\n' + huaweiDependency;

    return buildGradle.replace(/^(\s*)classpath 'com.android.tools.build(.*)/m, modifiedLine);
}

/*
 * Add developer.huawei.com to the repository repo list
 */
function addRepos(buildGradle) {
    // find the known line to match
    var match = buildGradle.match(/^(\s*)jcenter\(\)/m);
    var whitespace = match[1];

    // modify the line to add the necessary repo
    var huaweiMavenRepo = whitespace + 'maven { url \'https://developer.huawei.com/repo/\' } // huawei service dependency from cordova-plugin-agc-crash'
    var modifiedLine = match[0] + '\n' + huaweiMavenRepo;

    // modify the actual line
    buildGradle = buildGradle.replace(/^(\s*)jcenter\(\)/m, modifiedLine);

    // update the all projects grouping
    var allProjectsIndex = buildGradle.indexOf('allprojects');
    if (allProjectsIndex > 0) {
    // split the string on allprojects because jcenter is in both groups and we need to modify the 2nd instance
        var firstHalfOfFile = buildGradle.substring(0, allProjectsIndex);
        var secondHalfOfFile = buildGradle.substring(allProjectsIndex);

        // Add developer.huawei.com to the allprojects section of the string
        match = secondHalfOfFile.match(/^(\s*)jcenter\(\)/m);
        var huaweisMavenRepo = whitespace + 'maven { url \'https://developer.huawei.com/repo/\' } // huawei service dependency from cordova-plugin-agc-crash'
        modifiedLine = match[0] + '\n' + huaweisMavenRepo;
        // modify the part of the string that is after 'allprojects'
        secondHalfOfFile = secondHalfOfFile.replace(/^(\s*)jcenter\(\)/m, modifiedLine);

        // recombine the modified line
        buildGradle = firstHalfOfFile + secondHalfOfFile;
    } else {
        // this should not happen, but if it does, we should try to add the dependency to the buildscript
        match = buildGradle.match(/^(\s*)jcenter\(\)/m);
        var huaweisMavenRepo = whitespace + 'maven { url \'https://developer.huawei.com/repo/\' } // huawei service dependency from cordova-plugin-agc-crash'
        modifiedLine = match[0] + '\n' + huaweisMavenRepo;
        // modify the part of the string that is after 'allprojects'
        buildGradle = buildGradle.replace(/^(\s*)jcenter\(\)/m, modifiedLine);
    }
    return buildGradle;
}

/*
 * Helper function to write to the build.gradle that sits at the root of the project
 */
function writeRootBuildGradle(contents) {
    var target = path.join("platforms", "android", "build.gradle");
    fs.writeFileSync(target, contents);
}

module.exports = {
    modifyRootBuildGradle: function() {
    // be defensive and don't crash if the file doesn't exist
        if (!rootBuildGradleExists) {
            return;
        }

        var buildGradle = readRootBuildGradle();

        buildGradle = addDependencies(buildGradle);

        buildGradle = addRepos(buildGradle);

        writeRootBuildGradle(buildGradle);
    },

    restoreRootBuildGradle: function() {
    // be defensive and don't crash if the file doesn't exist
        if (!rootBuildGradleExists) {
          return;
        }

        var buildGradle = readRootBuildGradle();

        // remove any lines we added
        buildGradle = buildGradle.replace(/(?:^|\r?\n)(.*)cordova-plugin-agc-crash*?(?=$|\r?\n)/g, '');

        writeRootBuildGradle(buildGradle);
    }
};