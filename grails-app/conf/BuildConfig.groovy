grails.project.work.dir = 'target'
grails.project.target.level = 1.7

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
	}

	plugins {
		build(":release:2.2.1") {
			export = false
		}
	}
}
