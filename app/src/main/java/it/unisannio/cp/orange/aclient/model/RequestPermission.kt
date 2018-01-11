package it.unisannio.cp.orange.aclient.model


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 11/01/18
 *  Last Modified: $file.lastModified
 */


interface RequestPermission {
    fun requestPermission(vararg permission: String)
}