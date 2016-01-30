#!/bin/sh

#   Script to generate png icons in different resolutions from svg sources.
#   Copyright (C) 2016  Denis Nelubin
#
#   This program is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#
#   This program is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with this program.  If not, see <http://www.gnu.org/licenses/>.

BASEDIR="$(dirname "$(readlink -f "$0")")/.."

gen_icon() {
    file_name=$1
    img_size=$2
    res_folder=$3

    echo Converting misc/$file_name.svg to res/$res_folder/$file_name.png
    inkscape -z -e $BASEDIR/app/src/main/res/$res_folder/$file_name.png -w $img_size -h $img_size $BASEDIR/misc/$file_name.svg
}

gen_app_icon() {
    file_name=$1

    gen_icon $file_name 144 mipmap-xxhdpi
    gen_icon $file_name 96 mipmap-xhdpi
    gen_icon $file_name 72 mipmap-hdpi
    gen_icon $file_name 48 mipmap-mdpi
}

gen_app_icon shareopen
gen_app_icon share_link
gen_app_icon share_file
gen_app_icon open_link
gen_app_icon open_file
