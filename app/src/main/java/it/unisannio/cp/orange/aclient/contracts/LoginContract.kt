package it.unisannio.cp.orange.aclient.contracts


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 15/01/18
 *  Last Modified: $file.lastModified
 */


interface LoginContract {
    interface View{
        fun saveAndExit(result: Boolean)
    }

    interface Presenter{
        fun checkLogin(userName: String, password: String)
    }
}