package bg.qponer.qponerbackend.domain.service

class InvalidDataException(message: String) : Exception(message)

class MissingDataException(message: String) : Exception(message)