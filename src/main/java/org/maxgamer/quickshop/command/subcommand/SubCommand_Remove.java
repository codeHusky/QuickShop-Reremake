/*
 * This file is a part of project QuickShop, the name is SubCommand_Remove.java
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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.api.command.CommandHandler;
import org.maxgamer.quickshop.api.shop.Shop;
import org.maxgamer.quickshop.util.logging.container.ShopRemoveLog;

@AllArgsConstructor
public class SubCommand_Remove implements CommandHandler<Player> {

    private final QuickShop plugin;

    @Override
    public void onCommand(@NotNull Player sender, @NotNull String commandLabel, @NotNull String[] cmdArg) {
        BlockIterator bIt = new BlockIterator(sender, 10);

        if (!bIt.hasNext()) {
            plugin.text().of(sender, "not-looking-at-shop").send();
            return;
        }

        while (bIt.hasNext()) {
            final Block b = bIt.next();
            final Shop shop = plugin.getShopManager().getShop(b.getLocation());

            if (shop == null) {
                continue;
            }
            if (shop.getModerator().isModerator(sender.getUniqueId())
                    || QuickShop.getPermissionManager().hasPermission(sender, "quickshop.other.destroy")) {
                shop.delete();
                plugin.logEvent(new ShopRemoveLog(sender.getUniqueId(), "/qs remove command", shop.saveToInfoStorage()));
            } else {
                plugin.text().of(sender, "no-permission").send();
            }

            return;
        }

        plugin.text().of(sender, "not-looking-at-shop").send();
    }

}
