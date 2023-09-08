# Transport

当前最新进度分支：0.1.1

## 简介

该项目为跨系统文件传输项目

## 接口文档

### /transport

#### /uploadFile

POST /transport/uploadFile

Param:

| 参数名 | 参数类型 | 是否必须 | 描述 |
| -- | -- | -- | -- |
| location | String | false | 缓存目录下相对位置(若不存在则自动创建)，例："test/" |
| replace | boolean | false | 是否替换文件 |

Body:

form-data

| 参数名 | 参数类型 | 是否必须 | 描述 |
| -- | -- | -- | -- |
| file | File | true | 上传的文件 |

#### /deleteFile

DELETE /transport/deleteFile

Param:

| 参数名 | 参数类型 | 是否必须 | 描述 |
| -- | -- | -- | -- |
| location | String | false | 缓存目录下相对位置(若不存在则自动创建)，例："test/"。默认值："null/" |
| fileName | String | false | 目标文件名 |

#### /getFile

GET /transport/getFile

Param:

| 参数名 | 参数类型 | 是否必须 | 描述 |
| -- | -- | -- | -- |
| location | String | false | 缓存目录下相对位置(若不存在则自动创建)，例："test/"。默认值："" |
| fileName | String | false | 目标文件名 |
| download | boolean | false | 是否进行下载(默认值：false) |

#### /getDirInfo

GET /transport/

Param:

| 参数名 | 参数类型 | 是否必须 | 描述 |
| -- | -- | -- | -- |
| location | String | false | 缓存目录下相对位置(若不存在则自动创建)，例："test/"。默认值："" |
