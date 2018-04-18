# Course Project for GRAPH THEORY: ClassroomArrangement (Java)
## 1. Background
To reduce the number of commuters between classes, rearrange classes in THU
in a more compact manner.
## 2. Algorithm
### 2.1 Baseline Algorithm
Arrange classes with larger capacity in buildings with more classrooms.
### 2.2 Algorithm 1
Once pick a pair of adjacent classes. Maximize the common student number with
classes already arranged in buildings every time a new pair is added.
## 3. File Structure
### algorithm1
algorithm1 with *Generated Data Set*
### baseline
baseline algorithm with *Generated Data Set*
### baselineFromFile
baseline algorithm with *Real Data*
### connection
connection to url *(not used in project)*
### DataSet
*Generated Data Set*: dense edges, tight constraints
### dataSet2
*Generated Data Set*: sparse edges, tight constraints
### dataSet2
*Generated Data Set*: sparse edges, slack constraints
### experiment
algorithm  performance analysis using baseline algorithm & algorithm 1
with *Generated Data Set*
### gui
GUI layout and actoin functions
### lineCounter
count the total number of lines in all .java files in current project
### loadExcel
example code for reading excel *(not used in project)*
### login
auto log in info.tsinghua.edu.cn for course data *(failed, not used)*
### optimal
optimal solution for small-scale problem
### data file
#### 11.xls ~ 54.xls
arrangement result with *Real Data*
#### ConflictMap
map of common student of two adajacent classes in *Generated Data Set*
#### CourseList
list of courses in *Generated Data Set*
#### data.txt, data.xls
real course data
#### MiniExperiment.txt
small-scale problem performance experiment result
#### RealExperiment.txt
large-scale problem performance experiment result
#### room.xls
real classroom building data
