/*
 * This file is a part of project QuickShop, the name is SubCommand_SilentSell.java
 *  Copyright (C) PotatoCraft Studio and contributors
 *
 *  This program is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.maxgamer.quickshop.command.subcommand;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.api.command.CommandHandler;
import org.maxgamer.quickshop.api.shop.Shop;
import org.maxgamer.quickshop.api.shop.ShopType;
import org.maxgamer.quickshop.util.MsgUtil;
import org.maxgamer.quickshop.util.Util;

import java.util.UUID;

@AllArgsConstructor
public class SubCommand_SilentSell implements CommandHandler<Player> {

    private final QuickShop plugin;

    @Override
    public void onCommand(@NotNull Player sender, @NotNull String commandLabel, @NotNull String[] cmdArg) {
        if (cmdArg.length < 1) {
            Util.debugLog("Exception on command! Canceling!");
            return;
        }

        Shop shop = plugin.getShopManager().getShopFromRuntimeRandomUniqueId(UUID.fromString(cmdArg[0]));

        if (shop == null || !shop.getModerator().isModerator(sender.getUniqueId())) {
            plugin.text().of(sender, "not-looking-at-shop").send();
            return;
        }

        shop.setShopType(ShopType.SELLING);
        shop.update();
        MsgUtil.sendControlPanelInfo(sender, shop);
        plugin.text().of(sender,
                "command.now-selling", Util.getItemStackName(shop.getItem())).send();
    }
}
