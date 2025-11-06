package com.moira.moorobo.global.exception

class MooRoboException(val errorCode: ErrorCode) : RuntimeException(errorCode.message) {
}