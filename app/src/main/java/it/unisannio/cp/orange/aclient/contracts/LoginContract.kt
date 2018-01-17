/*
 * Copyright (c)  2018 Raffaele Mignone
 *
 *        This file is part of  A Client
 *
 *        A Client is free software: you can redistribute it and/or modify
 *        it under the terms of the GNU General Public License as published by
 *        the Free Software Foundation, either version 2 of the License, or
 *        (at your option) any later version.
 *
 *        A Client is distributed in the hope that it will be useful,
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU General Public License for more details.
 *
 *        You should have received a copy of the GNU General Public License
 *        along with A Client.  If not, see <http://www.gnu.org/licenses/>.
 */

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